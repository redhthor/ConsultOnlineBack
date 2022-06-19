package com.miconsultorio.app.controllers;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miconsultorio.app.model.entities.vo.ConsultaMedicaVO;
import com.miconsultorio.app.model.services.IConsultasService;

@RestController
@RequestMapping("api/consultorio")
public class ConsultorioController {
	@Autowired
	private IConsultasService consultaService;
	
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
		resp.put("consulta", consultaService.guardarConsulta(consulta.toEntity()).block());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
}
