package com.miconsultorio.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miconsultorio.app.model.entities.Cita;
import com.miconsultorio.app.model.requestbody.RequestAgendaByDate;
import com.miconsultorio.app.model.services.IAgendaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/agenda")
public class AgendaController {
	@Autowired
	private IAgendaService agendaService;
	
	@PostMapping("/porFecha")
	public Flux<Cita> getAgendaByDate(@RequestBody RequestAgendaByDate req) {
		return agendaService.obtenerCitasPorFechaInicio(req.getFecha(), req.getIdUsuario());
	}
	
	@PostMapping("/cita")
	public Mono<Cita> guardarCita(@RequestBody Cita cita) {
		return agendaService.guardarCita(cita);
	}
	
}
