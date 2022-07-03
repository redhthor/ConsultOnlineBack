package com.miconsultorio.app.model.entities;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Paciente {
	@Id
	private String id;
	private String email;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String sexo;
	private Date fechaNacimiento;
	private List<String> alergias = new LinkedList<>();

	public Paciente() {
		super();
	}

	public String getFullName() {
		String fullName = "";
		fullName = this.nombre + " " + this.apellido1;
		fullName = this.apellido2 != null && !this.apellido2.isBlank() ? fullName + " " + this.apellido2 : fullName;
		return fullName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getAlergias() {
		return alergias;
	}

	public void setAlergias(List<String> alergias) {
		this.alergias = alergias;
	}

	@Override
	public String toString() {
		return "Paciente [id=" + id + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2
				+ ", sexo=" + sexo + ", fechaNacimiento=" + fechaNacimiento + "]";
	}

}
