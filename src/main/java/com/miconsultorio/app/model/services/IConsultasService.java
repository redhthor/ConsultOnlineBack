package com.miconsultorio.app.model.services;

import com.miconsultorio.app.model.entities.ConsultaMedica;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IConsultasService {

	public Mono<ConsultaMedica> guardarConsulta(ConsultaMedica consulta);
	
	public void eliminarConsulta(String id);
	
	public Mono<ConsultaMedica> buscarConsulta(String id);
	
	public Flux<ConsultaMedica> listarConsultarPorPaciente(String email);
	
}
