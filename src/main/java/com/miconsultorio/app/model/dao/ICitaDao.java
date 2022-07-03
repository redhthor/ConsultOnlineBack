package com.miconsultorio.app.model.dao;

import java.util.Date;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.miconsultorio.app.model.entities.Cita;

import reactor.core.publisher.Flux;

public interface ICitaDao extends ReactiveMongoRepository<Cita, String> {
	
	@Query(value = "{'fechaInicio':{ $gte: ?0, $lte: ?1}, 'doctor': ?2}", sort = "{'fechaInicio': 1}")
	public Flux<Cita> buscarPorFechaInicioAndDoctor(Date fecha, Date fechaEnd, String doctor);
	
}
