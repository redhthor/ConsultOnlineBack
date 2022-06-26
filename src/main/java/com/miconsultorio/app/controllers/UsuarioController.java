package com.miconsultorio.app.controllers;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.miconsultorio.app.model.entities.Usuario;
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
	
	@GetMapping("/usuarios")
	public Flux<Usuario> getUsuarios() {
		return service.findAll();
	}

	@PostMapping("/usuario") 
	public ResponseEntity<Map<String, Object>> guardarUsuario(@RequestBody @Valid UsuarioVO usuario, BindingResult result) {
		Map<String, Object> response = new LinkedHashMap<>();
		if(result.hasErrors()) {
			List<String> errors =  new LinkedList<>();
			result.getFieldErrors().forEach(obj -> {
				errors.add(obj.getDefaultMessage());
			});
			response.put("error", "Solicitud no valida");
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		if(usuario.getId() == null || usuario.getId().isBlank()) {
			Usuario find = service.findByCorreo(usuario.getCorreo()).block();
			if(find != null) {
				response.put("error", "Ya existe un usuario con el correo " + usuario.getCorreo() + " registrado");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		}
		String encoded = usuario.getId() == null ? encoder.encode(usuario.getPassword()) : usuario.getPassword();
		logger.info("Password encriptado {}",encoded);
		usuario.setPassword(encoded);
		Mono<Usuario> newUser = service.save(usuario.toEntity());
		response.put("user", newUser.block());
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
}
