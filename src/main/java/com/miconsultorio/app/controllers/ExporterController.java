package com.miconsultorio.app.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miconsultorio.app.excepciones.NoEncontradoException;
import com.miconsultorio.app.model.services.IPdfExporter;

@RestController
@RequestMapping("exporter")
public class ExporterController {
	@Autowired
	private IPdfExporter exporter;
	
	private static final Logger logger = LoggerFactory.getLogger(ExporterController.class);

	@PostMapping("/consultas")
	public ResponseEntity<Map<String, Object>> exportConsultaToPDF(HttpServletResponse response, @RequestBody String consultaId) {
		Map<String, Object> resp = new LinkedHashMap<>();
		try {
			logger.info("Exportando a pdf {}",consultaId);
			exporter.exportarConsultaAPdf(response, consultaId);
		} catch(NoEncontradoException ex) {
			resp.put("error", "Ocurrió un error al procesar la solicitud");
			logger.error("Excepcion al exportar a pdf la consulta {}: {}",consultaId, ex.getMessage());
			return new ResponseEntity<>(resp,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setContentType("application/pdf");
		resp.put("mensaje", "Exito");
		return new ResponseEntity<>(resp,HttpStatus.OK);
	}
	
}
