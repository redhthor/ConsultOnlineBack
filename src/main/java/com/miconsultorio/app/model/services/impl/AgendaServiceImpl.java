package com.miconsultorio.app.model.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miconsultorio.app.excepciones.NoEncontradoException;
import com.miconsultorio.app.model.dao.ICitaDao;
import com.miconsultorio.app.model.entities.Cita;
import com.miconsultorio.app.model.services.IAgendaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AgendaServiceImpl implements IAgendaService {
	@Autowired
	private ICitaDao citaDao;
	
	private static final Logger logger = LoggerFactory.getLogger(AgendaServiceImpl.class);
	
	@Override
	@Transactional(readOnly = false)
	public Mono<Cita> guardarCita(Cita cita) {
		return citaDao.save(cita);
	}
	
	@Override
	public Flux<Cita> obtenerCitasPorFechaInicio(Date fecha, String doctor) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fechaFinString = sdf1.format(fecha) + " 23:59:59";
		String fechaInicioString = sdf1.format(fecha) + " 00:00:00";
		Date fechaInicio = null;
		Date fechaFin = null;
		try {
			fechaInicio = sdf2.parse(fechaInicioString);
			fechaFin = sdf2.parse(fechaFinString);
		} catch(ParseException ex) {
			logger.error("Ocurrió un error al convertir las fechas: {}", ex.getMessage());
		}
		return citaDao.buscarPorFechaInicioAndDoctor(fechaInicio, fechaFin, doctor);
	}
	
	@Override
	public void eliminarCita(String id) throws NoEncontradoException {
		if(citaDao.findById(id).block() == null) {
			throw new NoEncontradoException("No existe la cita a eliminar");
		}
		citaDao.deleteById(id);
	}
}
