package com.miconsultorio.app.controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miconsultorio.app.model.entities.Usuario;
import com.miconsultorio.app.model.entities.vo.PasswordUpdateVO;
import com.miconsultorio.app.model.entities.vo.UsuarioVO;
import com.miconsultorio.app.model.services.IUsuarioService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class UsuarioController {
	@Autowired
	private IUsuarioService service;
	
	@Autowired
	private PasswordEncoder encoder;
	
	private Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	private static final String ERROR = "error";
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/usuarios")
	public Flux<Usuario> getUsuarios() {
		return service.findAll();
	}

	@PostMapping("/usuario") 
	public ResponseEntity<Map<String, Object>> registrarUsuario(@RequestBody @Valid UsuarioVO usuario, BindingResult result) {
		Map<String, Object> response = new LinkedHashMap<>();
		if(result.hasErrors()) {
			List<String> errors =  new LinkedList<>();
			result.getFieldErrors().forEach(obj -> 
				errors.add(obj.getDefaultMessage())
			);
			response.put(ERROR, "Solicitud no valida");
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		if(usuario.getId() == null || usuario.getId().isBlank()) {
			Usuario find = service.findByCorreo(usuario.getCorreo()).block();
			if(find != null) {
				response.put(ERROR, "Ya existe un usuario con el correo " + usuario.getCorreo() + " registrado");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		}
		String encoded = usuario.getId() == null ? encoder.encode(usuario.getPassword()) : usuario.getPassword();
		usuario.setPassword(encoded);
		Mono<Usuario> newUser = service.save(usuario.toEntity());
		response.put("user", newUser.block());
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_USER")
	@PostMapping("/usuarioUpdate") 
	public ResponseEntity<Map<String, Object>> guardarUsuario(@RequestBody UsuarioVO usuario) {
		String accessDenied = "Acceso denegado";
		Map<String, Object> response = new LinkedHashMap<>();
		SecurityContext context = SecurityContextHolder.getContext();
		String userLogged = context.getAuthentication().getName();
		if(!context.getAuthentication().isAuthenticated() || !userLogged.equals(usuario.getCorreo())) {
			logger.info("Acceso denegado 1 {}",context.getAuthentication().isAuthenticated());
			logger.info("Acceso denegado 1 {}, {}",userLogged, usuario.getCorreo());
			response.put(ERROR, accessDenied);
			return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
		}		
		if(usuario.getId() == null || usuario.getId().isBlank() 
				|| usuario.getFechaNacimiento() == null 
				|| usuario.getNombre().isBlank() 
				|| usuario.getApellidoPaterno().isBlank()) {
			logger.info("Acceso denegado 2, {}", usuario);
			logger.info("Solicitud de actualización de usuario no válida {}", usuario.getId());
			response.put(ERROR, "Solicitud no válida");
			response.put("mensaje", "Información no válida");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		Usuario u = service.findByCorreo(userLogged).block();
		if(u == null) {
			response.put(ERROR,  accessDenied);
			return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
		}
		u.setNombre(usuario.getNombre());
		u.setApellidoPaterno(usuario.getApellidoPaterno());
		u.setApellidoMaterno(usuario.getApellidoMaterno());
		u.setCedula(usuario.getCedula());
		u.setTitulo(usuario.getTitulo());
		u.setFechaNacimiento(usuario.getFechaNacimiento());
		Mono<Usuario> newUser = service.save(u);
		response.put("user", newUser.block());
		response.put("mensaje", "Se guardó correctamente la información");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_USER")
	@GetMapping("/getUserData")
	public ResponseEntity<Object> getUserLoggedData() {
		Map<String, Object> resp = new HashMap<>();
		SecurityContext context = SecurityContextHolder.getContext();
		if(!context.getAuthentication().isAuthenticated()) {
			resp.put(ERROR, "Acceso denegado");
			return new ResponseEntity<>(resp, HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(service.findByCorreo(context.getAuthentication().getName()).block(),HttpStatus.OK);
	}
	
	@Secured("ROLE_USER")
	@PostMapping("/usuario/passwordUpdate")
	public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody PasswordUpdateVO datos) {
		Map<String, Object> resp = new LinkedHashMap<>();
		SecurityContext context = SecurityContextHolder.getContext();
		if(!context.getAuthentication().isAuthenticated() || !context.getAuthentication().getName().equals(datos.getUser())) {
			resp.put(ERROR, "Acceso denegado, no puedes actualizar la contraseña");
			resp.put("mensaje", "No tienes permiso para actualizar la contraseña");
			return new ResponseEntity<>(resp,HttpStatus.FORBIDDEN);
		}
		Usuario user = this.service.findByCorreo(datos.getUser()).block();
		if(user == null) {
			resp.put(ERROR, "No fue posible actualizar la contraseña");
			resp.put("mensaje", "No se encontró el usuario solicitado");
			return new ResponseEntity<>(resp,HttpStatus.FORBIDDEN);
		}
		user.setPassword(encoder.encode(datos.getPassword()));
		user = service.save(user).block();
		resp.put("mensaje", "Se actualizó correctamente la contraseña");
		return new ResponseEntity<>(resp,HttpStatus.OK);
	}
	
}
