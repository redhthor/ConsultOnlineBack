package com.miconsultorio.app.model.services;

import com.miconsultorio.app.model.entities.CategoriaEnfermedades;

import reactor.core.publisher.Flux;

public interface ICatalogosService {

	public Flux<CategoriaEnfermedades> getCategoriasEnfermedades();
	
}
