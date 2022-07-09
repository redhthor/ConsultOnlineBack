package com.miconsultorio.app.model.entities;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.miconsultorio.app.model.entities.bo.Medicacion;

@Document
public class ConsultaMedica {
	@Id
	private String id;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Date fecha;
	private Paciente paciente;
	private String doctor;
	private Float peso;
	private Float estatura;
	private Float temperatura;
	private String diagnostico;
	private List<Medicacion> medicacion;
	private String observaciones;
	private String presion;
	private List<String> sintomas;

	public ConsultaMedica() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnositco) {
		this.diagnostico = diagnositco;
	}

	public List<Medicacion> getMedicacion() {
		return medicacion;
	}

	public void setMedicacion(List<Medicacion> medicacion) {
		this.medicacion = medicacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}

	public Float getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Float temperatura) {
		this.temperatura = temperatura;
	}

	public List<String> getSintomas() {
		return sintomas;
	}

	public void setSintomas(List<String> sintomas) {
		this.sintomas = sintomas;
	}

	public Float getEstatura() {
		return estatura;
	}

	public void setEstatura(Float estatura) {
		this.estatura = estatura;
	}

	public String getPresion() {
		return presion;
	}

	public void setPresion(String presion) {
		this.presion = presion;
	}

	@Override
	public String toString() {
		return "ConsultaMedica [id=" + id + ", fecha=" + fecha + ", paciente=" + paciente + ", doctor=" + doctor
				+ ", peso=" + peso + ", estatura=" + estatura + ", temperatura=" + temperatura + ", diagnostico="
				+ diagnostico + ", medicacion=" + medicacion + ", observaciones=" + observaciones + ", presion="
				+ presion + ", sintomas=" + sintomas + "]";
	}

}
