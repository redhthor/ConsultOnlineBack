package com.miconsultorio.app.controllers;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miconsultorio.app.model.entities.Usuario;
import com.miconsultorio.app.model.services.IUsuarioService;

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
	public ResponseEntity<Map<String, Object>> getUsuarios() {
		Map<String, Object> response = new LinkedHashMap<>();
		LinkedList<Usuario> users = new LinkedList<>();
		service.findAll().doOnNext(users::add).blockLast();
		response.put("users", users);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/usuario") 
	public ResponseEntity<Map<String, Object>> guardarUsuario(@RequestBody Usuario usuario) {
		Map<String, Object> response = new LinkedHashMap<>();
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
		Mono<Usuario> newUser = service.save(usuario);
		response.put("user", newUser.block());
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
}
