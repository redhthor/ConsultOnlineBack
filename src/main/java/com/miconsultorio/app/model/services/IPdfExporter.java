package com.miconsultorio.app.model.services;

import javax.servlet.http.HttpServletResponse;

import com.miconsultorio.app.excepciones.NoEncontradoException;

public interface IPdfExporter {
	
	public void exportarConsultaAPdf(HttpServletResponse response, String id) throws NoEncontradoException;
	
}
