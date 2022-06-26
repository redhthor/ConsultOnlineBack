package com.miconsultorio.app.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miconsultorio.app.model.dao.IPacienteDao;
import com.miconsultorio.app.model.entities.Paciente;
import com.miconsultorio.app.model.services.IPacienteService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PacienteServiceImpl implements IPacienteService {
	@Autowired
	private IPacienteDao pacienteDao;
	
	@Override
	@Transactional(readOnly = false)
	public Mono<Paciente> guardarPaciente(Paciente p) {
		return pacienteDao.save(p);
	}

	@Override
	public Flux<Paciente> getAllPacientes() {
		return pacienteDao.findAll();
	}

	@Override
	public Mono<Paciente> buscarPaciente(String id) {
		return pacienteDao.findById(id);
	}

	@Override
	public Mono<Paciente> buscarPacientePorEmail(String email) {
		return pacienteDao.findByEmail(email);
	}	
}
