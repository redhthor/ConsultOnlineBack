package com.miconsultorio.app.model.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "categoriasEnfermedades")
public class CategoriaEnfermedades {
	@Id
	private String id;
	private String descripcion;

	public CategoriaEnfermedades() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "CategoriaEnfermedades [id=" + id + ", descripcion=" + descripcion + "]";
	}

}
