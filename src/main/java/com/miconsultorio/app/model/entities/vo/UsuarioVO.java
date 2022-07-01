package com.miconsultorio.app.model.entities.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.miconsultorio.app.model.entities.Usuario;

public class UsuarioVO implements Serializable {
	private String id;
	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;
	private String titulo;
	private String cedula;
	@NotBlank(message = "El apellido paterno es obligatorio")
	private String apellidoPaterno;
	private String apellidoMaterno;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "La fecha de nacimiento es obligatoria")
	private Date fechaNacimiento;
	@NotBlank(message = "El correo debe contener un valor")
	@Email(message = "El correo no es valido.")
	private String correo;
	@NotBlank(message = "El password es un campo obligatorio")
	private String password;
	private Integer status;
	private List<String> roles;

	public UsuarioVO() {
		super();
	}

	public Usuario toEntity() {
		Usuario u = new Usuario();
		u.setId(this.id);
		u.setNombre(this.nombre);
		u.setTitulo(titulo);
		u.setCedula(cedula);
		u.setApellidoPaterno(this.apellidoPaterno);
		u.setApellidoMaterno(this.apellidoMaterno);
		u.setFechaNacimiento(this.fechaNacimiento);
		u.setCorreo(this.correo);
		u.setPassword(this.password);
		u.setStatus(this.status);
		u.setRoles(this.roles);
		return u;
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

	private static final long serialVersionUID = 818123L;
}
