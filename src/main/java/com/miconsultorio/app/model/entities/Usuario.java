package com.miconsultorio.app.model.entities;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public class Usuario {
	@Id
	private String id;
	private String titulo;
	private String cedula;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date fechaNacimiento;
	private String correo;
	@JsonIgnore
	private String password;
	private Integer status;
	@JsonIgnore
	private List<String> roles;

	public Usuario() {
		status = 1;
	}

	public Usuario(String nombre, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento, String correo,
			String password, List<String> roles) {
		super();
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.fechaNacimiento = fechaNacimiento;
		this.correo = correo;
		this.password = password;
		this.roles = roles;
	}

	public String fullName() {
		String fullName = titulo != null && !titulo.isBlank() ? titulo : "";
		fullName = fullName + this.nombre + " " + this.apellidoPaterno;
		fullName = this.apellidoMaterno != null && !this.apellidoMaterno.isBlank() ? fullName + " " + this.apellidoMaterno : fullName; 
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

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
}
