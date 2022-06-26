package com.miconsultorio.app.model.services.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miconsultorio.app.model.dao.IConsultaMedicaDao;
import com.miconsultorio.app.model.entities.ConsultaMedica;
import com.miconsultorio.app.model.services.IConsultasService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ConsultaServiceImpl implements IConsultasService {
	@Autowired
	private IConsultaMedicaDao consultaDao;

	@Override
	public Mono<ConsultaMedica> guardarConsulta(ConsultaMedica consulta) {
		return consultaDao.save(consulta);
	}

	@Override
	public void eliminarConsulta(String id) {
		ConsultaMedica cm = consultaDao.findById(id).block();
		if(cm == null) {
			return;
		}
		consultaDao.delete(cm);
	}
	
	@Override
	public Mono<ConsultaMedica> buscarConsulta(String id) {
		return consultaDao.buscarPorId(id);
	}
	
	@Override
	public Flux<ConsultaMedica> listarConsultarPorPaciente(String email) {
		return consultaDao.findByPacienteEmail(email);
	}

}
