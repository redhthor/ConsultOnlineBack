package com.miconsultorio.app.model.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.miconsultorio.app.model.entities.Paciente;

import reactor.core.publisher.Mono;

public interface IPacienteDao extends ReactiveMongoRepository<Paciente, String> {

	public Mono<Paciente> findByEmail(String email);
	
}
