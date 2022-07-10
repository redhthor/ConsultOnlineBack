package com.miconsultorio.app.model.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.miconsultorio.app.model.entities.CategoriaEnfermedades;

import reactor.core.publisher.Flux;

public interface ICatalogoEnfermedadesDao extends ReactiveMongoRepository<CategoriaEnfermedades, String> {

	@Query(value = "{}", sort = "{'descripcion': 1}")
	public Flux<CategoriaEnfermedades> findAllOrderSortByDescripcion();
	
}
