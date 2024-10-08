package com.miconsultorio.app.model.services;

import java.util.Date;

import com.miconsultorio.app.model.entities.Cita;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAgendaService {
	
	public Flux<Cita> obtenerCitasPorFecha(Date fecha, String idUsuario);
	
	public Mono<Cita> guardarCita(Cita cita);
	
}
