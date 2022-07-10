package com.miconsultorio.app.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.miconsultorio.app.model.dao.ICatalogoEnfermedadesDao;
import com.miconsultorio.app.model.entities.CategoriaEnfermedades;
import com.miconsultorio.app.model.services.ICatalogosService;

import reactor.core.publisher.Flux;

@Service
public class CatalogosServiceImpl implements ICatalogosService {
	@Autowired
	private ICatalogoEnfermedadesDao catalogoEnfermedades;

	@Override
	@Transactional(readOnly = true)
	public Flux<CategoriaEnfermedades> getCategoriasEnfermedades() {
		return catalogoEnfermedades.findAllOrderSortByDescripcion();
	}

}
