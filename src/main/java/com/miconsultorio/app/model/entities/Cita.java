package com.miconsultorio.app.model.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document
public class Cita {
	@Id
	private String id;
	private String paciente;
	private String pacienteEmail;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fechaInicio;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fechaFin;
	private String idUsuario;
	private Float duracion;
	
	public Cita() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPaciente() {
		return paciente;
	}

	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}

	public String getPacienteEmail() {
		return pacienteEmail;
	}

	public void setPacienteEmail(String pacienteEmail) {
		this.pacienteEmail = pacienteEmail;
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

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Float getDuracion() {
		return duracion;
	}

	public void setDuracion(Float duracion) {
		this.duracion = duracion;
	}

	@Override
	public String toString() {
		return "Cita [id=" + id + ", paciente=" + paciente + ", pacienteEmail=" + pacienteEmail + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", idUsuario=" + idUsuario + ", duracion=" + duracion + "]";
	}
	

}
