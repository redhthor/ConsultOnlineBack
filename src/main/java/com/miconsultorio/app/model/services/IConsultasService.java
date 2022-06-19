package com.miconsultorio.app.model.services;

import com.miconsultorio.app.model.entities.ConsultaMedica;

import reactor.core.publisher.Mono;

public interface IConsultasService {

	public Mono<ConsultaMedica> guardarConsulta(ConsultaMedica consulta);
	
	public void eliminarConsulta(String id);
	
}
