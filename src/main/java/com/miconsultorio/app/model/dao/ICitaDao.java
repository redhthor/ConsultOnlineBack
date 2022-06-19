package com.miconsultorio.app.model.dao;

import java.util.Date;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.miconsultorio.app.model.entities.Cita;

import reactor.core.publisher.Flux;

public interface ICitaDao extends ReactiveMongoRepository<Cita, String> {
	
	public Flux<Cita> findByFechaInicioAndIdUsuario(Date fecha, String idUsuario);
	
}
