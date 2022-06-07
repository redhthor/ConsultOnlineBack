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
	private Date fecha;
	private String hora;
	private String idUsuario;
	private Float duracion;
	
	public Cita() {
	}

	public Cita(String paciente, String pacienteEmail, Date fecha) {
		super();
		this.paciente = paciente;
		this.pacienteEmail = pacienteEmail;
		this.fecha = fecha;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
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
		return "Cita [id=" + id + ", paciente=" + paciente + ", pacienteEmail=" + pacienteEmail + ", fecha=" + fecha
				+ ", hora=" + hora + ", idUsuario=" + idUsuario + ", duracion=" + duracion + "]";
	}

}
