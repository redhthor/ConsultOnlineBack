package com.miconsultorio.app.model.entities.vo;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.miconsultorio.app.model.entities.Paciente;

public class PacienteVO {
	private String id;
	private String email;
	@NotBlank
	private String nombre;
	@NotBlank
	private String apellido1;
	private String apellido2;
	@NotBlank
	@Size(max = 1)
	@Pattern(regexp = "[M|H]")
	private String sexo;
	@NotNull
	private Date fechaNacimiento;
	private List<String> alergias;

	public PacienteVO() {
		super();
	}

	public static PacienteVO fromEntity(Paciente entity) {
		PacienteVO p = new PacienteVO();
		p.setId(entity.getId());
		p.setEmail(entity.getEmail());
		p.setNombre(entity.getNombre());
		p.setApellido1(entity.getApellido1());
		p.setApellido2(entity.getApellido2());
		p.setSexo(entity.getSexo());
		p.setFechaNacimiento(entity.getFechaNacimiento());
		p.setAlergias(entity.getAlergias());
		return p;
	}

	public Paciente toEntity() {
		Paciente p = new Paciente();
		p.setId(this.id);
		p.setEmail(email);
		p.setNombre(this.nombre);
		p.setApellido1(this.apellido1);
		p.setApellido2(apellido2);
		p.setSexo(sexo);
		p.setFechaNacimiento(fechaNacimiento);
		p.setAlergias(alergias);
		return p;
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
