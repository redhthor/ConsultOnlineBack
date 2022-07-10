package com.miconsultorio.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miconsultorio.app.model.entities.CategoriaEnfermedades;
import com.miconsultorio.app.model.services.ICatalogosService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("api/catalogo")
public class CatalogosController {
	@Autowired
	private ICatalogosService catalogosService;
	
	@Secured("ROLE_USER")
	@GetMapping("categoriaEnfermedades")
	public Flux<CategoriaEnfermedades> getCategoriaEnfermedades() {
		return catalogosService.getCategoriasEnfermedades();
	}
	
}
