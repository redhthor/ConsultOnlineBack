package com.miconsultorio.app.model.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document
public class Cita {
	@Id
	private String id;
	private String nombre;
	private Paciente paciente;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date fechaInicio;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date fechaFin;
	private String doctor;
	private String comentarios;
	
	public Cita() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	@Override
	public String toString() {
		return "Cita [id=" + id + ", nombre=" + nombre + ", paciente=" + paciente + ", fechaInicio=" + fechaInicio
				+ ", fechaFin=" + fechaFin + ", doctor=" + doctor + "]";
	}

}
