package com.miconsultorio.app.controllers;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miconsultorio.app.model.entities.ConsultaMedica;
import com.miconsultorio.app.model.entities.Paciente;
import com.miconsultorio.app.model.entities.vo.ConsultaMedicaVO;
import com.miconsultorio.app.model.entities.vo.PacienteVO;
import com.miconsultorio.app.model.services.IConsultasService;
import com.miconsultorio.app.model.services.impl.PacienteServiceImpl;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/consultorio")
public class ConsultorioController {
	@Autowired
	private IConsultasService consultaService;
	@Autowired
	private PacienteServiceImpl pacienteService;
	
	private static final Logger logger = LoggerFactory.getLogger(ConsultorioController.class);
	
	@Secured("ROLE_USER")
	@PostMapping("guardarConsulta")
	public ResponseEntity<Map<String, Object>> guardarConsulta(@RequestBody @Valid ConsultaMedicaVO consulta, BindingResult result) {
		Map<String, Object> resp = new LinkedHashMap<>();
		if(result.hasErrors()) {
			List<String> errors = new LinkedList<>();
			result.getFieldErrors().forEach(
					err -> errors.add("El campo " + err.getField() + " " + err.getDefaultMessage())
			);
			resp.put("errors", errors);
			resp.put("error", "Solicitud no válida");
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		if(consulta.getPaciente() != null 
				&& (consulta.getPaciente().getId() == null || consulta.getPaciente().getId().isBlank()) 
				&& (consulta.getPaciente().getEmail() != null && !consulta.getPaciente().getEmail().isBlank())) {
			Paciente p = pacienteService.buscarPacientePorEmail(consulta.getPaciente().getEmail()).block();
			logger.info("Valor encontrado del paciente con email: {}", p);
			if (p == null) {
				if(consulta.getPaciente().getId() != null) {
					consulta.getPaciente().setId(null);
				}
				p = pacienteService.guardarPaciente(consulta.getPaciente().toEntity()).block();
				consulta.setPaciente(PacienteVO.fromEntity(p));
				logger.info("Se guarda el paciente en la base de datos");
			} else {
				logger.info("El paciente ya se encuentra en la base de datos");
				consulta.setPaciente(PacienteVO.fromEntity(p));
			}
		} else if(consulta.getPaciente() != null && consulta.getId() != null && !consulta.getId().isBlank()) {
			// Updating allergies
			Paciente p = this.pacienteService.buscarPacientePorEmail(consulta.getPaciente().getEmail()).block();
			if(p != null) {
				logger.info("Actualizando alergias {}",consulta.getPaciente().getAlergias());
				p.setAlergias(consulta.getPaciente().getAlergias());
				this.pacienteService.guardarPaciente(p).subscribe();
			}
			logger.info("El paciente ya se había guardado anteriormente en la base de datos");
		}
		resp.put("consulta", consultaService.guardarConsulta(consulta.toEntity()).block());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@Secured("ROLE_USER")
	@PostMapping("/consulta")
	public ResponseEntity<Object> buscarConsulta(@RequestBody String id) {
		logger.info("Buscando consulta con id: {}",id);
		Map<String, Object> resp = new LinkedHashMap<>();
		if(id == null || id.isBlank()) {
			resp.put("error", "El id de la consulta solicitada no es válido");
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		ConsultaMedica consulta = null;
		try {
			consulta = consultaService.buscarConsulta(id).block();
		} catch(IllegalArgumentException il) {
			logger.error("Excepcion de argumento ilegal: {}", il.getMessage());
		}
		if(consulta == null) {
			resp.put("mensaje","No se encontró una consulta con el id "+id);
			return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(consulta, HttpStatus.OK);
	}

	@Secured("ROLE_USER")
	@PostMapping("buscarPaciente")
	public Mono<Paciente> findPaciente(@RequestBody String email) {
		logger.info("Buscando paciente {}",email);
		return pacienteService.buscarPacientePorEmail(email);
	}
}
