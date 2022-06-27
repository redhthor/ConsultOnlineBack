package com.miconsultorio.app.model.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.miconsultorio.app.model.entities.ConsultaMedica;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IConsultaMedicaDao extends ReactiveMongoRepository<ConsultaMedica, String>{

	public Flux<ConsultaMedica> findByPacienteEmail(String email);
	
	@Query("{'id': ObjectId(?0)}")
	public Mono<ConsultaMedica> buscarPorId(String id);
	
}
