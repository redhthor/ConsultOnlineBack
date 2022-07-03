package com.miconsultorio.app.model.entities.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.miconsultorio.app.model.entities.Cita;
import com.miconsultorio.app.model.entities.Paciente;

public class CitaVO {
	private String id;
	@NotBlank
	@Size(min = 1, max = 50)
	private String nombre;
	private Paciente paciente;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	@NotNull(message = "Es un valor requerido")
	private Date fechaInicio;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	@NotNull(message = "Es un valor requerido")
	private Date fechaFin;
	@NotBlank(message = "Es necesario el usuario que crea el evento")
	private String doctor;
	
	public CitaVO() {
		super();
	}

	public Cita toEntity() {
		Cita c = new Cita();
		c.setId(id);
		c.setDoctor(doctor);
		c.setFechaFin(fechaFin);
		c.setFechaInicio(fechaInicio);
		c.setNombre(nombre);
		c.setPaciente(paciente);
		return c;
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

	@Override
	public String toString() {
		return "Cita [id=" + id + ", nombre=" + nombre + ", paciente=" + paciente + ", fechaInicio=" + fechaInicio
				+ ", fechaFin=" + fechaFin + ", doctor=" + doctor + "]";
	}

}
