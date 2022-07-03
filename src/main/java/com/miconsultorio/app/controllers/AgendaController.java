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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miconsultorio.app.model.entities.Cita;
import com.miconsultorio.app.model.entities.vo.CitaVO;
import com.miconsultorio.app.model.requestbody.RequestAgendaByDate;
import com.miconsultorio.app.model.services.IAgendaService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/agenda")
public class AgendaController {
	@Autowired
	private IAgendaService agendaService;
	
	private static final Logger logger = LoggerFactory.getLogger(AgendaController.class);
	
	@Secured("ROLE_USER")
	@PostMapping("/porFecha")
	public Flux<Cita> getAgendaByDate(@RequestBody RequestAgendaByDate req) {
		String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!usuario.equals(req.getIdUsuario())) {
			logger.info("El usuario que solicita no pertence al usuario autenticado, {}, {}", usuario, req.getIdUsuario());
			return Flux.just();
		}
		return agendaService.obtenerCitasPorFechaInicio(req.getFecha(), usuario);
	}
	
	@Secured("ROLE_USER")
	@PostMapping("/cita")
	public ResponseEntity<Map<String, Object>> guardarCita(@RequestBody @Valid CitaVO cita, BindingResult result) {
		SecurityContext context = SecurityContextHolder.getContext();
		Map<String, Object> resp = new LinkedHashMap<>();
		if(!context.getAuthentication().isAuthenticated()) {
			resp.put("error", "Tu sesión ha finalizado, vuelve a intentarlo");
			return new ResponseEntity<>(resp, HttpStatus.FORBIDDEN);
		}
		if(result.hasErrors()) {
			List<String> errors = new LinkedList<>();
			result.getFieldErrors().forEach(err -> 
				errors.add("Error: el campo " + err.getField() + " " + err.getDefaultMessage())
			);
			resp.put("errors", errors);
			resp.put("error", "Solicitud no válida");
			return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
		}
		Cita c = agendaService.guardarCita(cita.toEntity()).block();
		resp.put("cita", c);
		resp.put("mensaje", "La cita se guardo correctamente");
		return new ResponseEntity<>(resp,HttpStatus.OK);
	}
	
}
