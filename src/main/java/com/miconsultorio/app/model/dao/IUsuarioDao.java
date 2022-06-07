package com.miconsultorio.app.model.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.miconsultorio.app.model.entities.Usuario;

import reactor.core.publisher.Mono;

public interface IUsuarioDao extends ReactiveMongoRepository<Usuario, String> {

	public Mono<Usuario> findByCorreo(String correo);
	
}
