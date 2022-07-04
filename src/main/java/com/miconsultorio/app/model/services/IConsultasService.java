package com.miconsultorio.app.model.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.miconsultorio.app.excepciones.InternalException;
import com.miconsultorio.app.model.entities.ConsultaMedica;
import com.miconsultorio.app.model.requestbody.RequestHistorialConsultas;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IConsultasService {

	public Mono<ConsultaMedica> guardarConsulta(ConsultaMedica consulta);
	
	public void eliminarConsulta(String id);
	
	public Mono<ConsultaMedica> buscarConsulta(String id);
	
	public Flux<ConsultaMedica> listarConsultarPorPaciente(String email);
	
	public Page<ConsultaMedica> buscarHistorialDeConsultas(RequestHistorialConsultas req, Pageable pageable) throws InternalException;
	
}
