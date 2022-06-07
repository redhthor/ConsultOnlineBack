package com.miconsultorio.app.model.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miconsultorio.app.model.dao.ICitaDao;
import com.miconsultorio.app.model.entities.Cita;
import com.miconsultorio.app.model.services.IAgendaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AgendaServiceImpl implements IAgendaService {
	@Autowired
	private ICitaDao citaDao;
	
	@Override
	public Mono<Cita> guardarCita(Cita cita) {
		return citaDao.save(cita);
	}
	
	@Override
	public Flux<Cita> obtenerCitasPorFecha(Date fecha, String idUsuario) {
		return citaDao.findByFechaAndIdUsuario(fecha, idUsuario);
	}
}
