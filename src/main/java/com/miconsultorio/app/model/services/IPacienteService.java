package com.miconsultorio.app.model.services;

import com.miconsultorio.app.model.entities.Paciente;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPacienteService {

	public Mono<Paciente> guardarPaciente(Paciente p);
	
	public Flux<Paciente> getAllPacientes();
	
	public Mono<Paciente> buscarPaciente(String id);

	public Mono<Paciente> buscarPacientePorEmail(String email);
	
}
