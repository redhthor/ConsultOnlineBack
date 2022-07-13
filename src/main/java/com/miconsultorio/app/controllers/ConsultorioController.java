package com.miconsultorio.app.controllers;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miconsultorio.app.excepciones.InternalException;
import com.miconsultorio.app.model.entities.ConsultaMedica;
import com.miconsultorio.app.model.entities.Paciente;
import com.miconsultorio.app.model.entities.vo.ConsultaMedicaVO;
import com.miconsultorio.app.model.entities.vo.PacienteVO;
import com.miconsultorio.app.model.requestbody.RequestHistorialConsultas;
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
	private static final String ERROR = "error";
	private static final String BAD_REQUEST = "Solicitud no válida";
	
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
			resp.put(ERROR, BAD_REQUEST);
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		if(consulta.getPaciente() != null 
				&& (consulta.getPaciente().getId() == null || consulta.getPaciente().getId().isEmpty()) 
				&& (consulta.getPaciente().getEmail() != null && !consulta.getPaciente().getEmail().isEmpty())) {
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
		} else if(consulta.getPaciente() != null && consulta.getId() != null && !consulta.getId().isEmpty()) {
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
		if(id == null || id.isEmpty()) {
			resp.put(ERROR, "El id de la consulta solicitada no es válido");
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
	
	@Secured("ROLE_USER") 
	@PostMapping("/historialConsultas")
	public ResponseEntity<Object> historialConsultas(@RequestBody @Valid RequestHistorialConsultas req, BindingResult result) {
		Map<String, Object> response = new LinkedHashMap<>();
		logger.info("Buscando historial de consultas");
		if(result.hasErrors()) {
			List<String> errors = new LinkedList<>();
			result.getFieldErrors().forEach(err -> errors.add("El campo " + err.getField() + " " + err.getDefaultMessage()));
			logger.info(BAD_REQUEST);
			response.put(ERROR, BAD_REQUEST);
			response.put("errors", errors);
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		Page<ConsultaMedica> resultado = null;
		try {
			Pageable page = PageRequest.of(req.getPagina()-1, req.getRegistrosPorPagina());
			resultado = consultaService.buscarHistorialDeConsultas(req, page);
			logger.info("Información encontrada");
		} catch(InternalException ex) {
			logger.error("Ocurrió una excepcion: {}, datos: {}",ex.getMessage(), req);
			response.put(ERROR, "Ocurrió un error, intentalo más tarde");
			response.put("mensaje", "Ocurrió un error, intentalo más tarde");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}
}
