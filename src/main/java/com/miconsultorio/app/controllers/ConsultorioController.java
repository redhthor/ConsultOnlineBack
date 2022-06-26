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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
		
	@PostMapping("guardarConsulta")
	public ResponseEntity<Map<String, Object>> guardarConsulta(@RequestBody @Valid ConsultaMedicaVO consulta, BindingResult result) {
		Map<String, Object> resp = new LinkedHashMap<>();
		if(result.hasErrors()) {
			List<String> errors = new LinkedList<>();
			result.getFieldErrors().forEach( err -> {
				errors.add("El campo " + err.getField() + " " + err.getDefaultMessage());
			});
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
			logger.info("El paciente ya se había guardado anteriormente en la base de datos");
		}
		resp.put("consulta", consultaService.guardarConsulta(consulta.toEntity()).block());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping("buscarPaciente")
	public Mono<Paciente> findPaciente(@RequestBody String email) {
		logger.info("Buscando paciente {}",email);
		return pacienteService.buscarPacientePorEmail(email);
	}
}
