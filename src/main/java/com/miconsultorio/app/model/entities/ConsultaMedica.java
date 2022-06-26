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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	private Date fecha;
	private Paciente paciente;
	private String doctor;
	private Float peso;
	private Float temperatura;
	private String diagnostico;
	private List<Medicacion> medicacion;
	private String observaciones;
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

	public String getDiagnositco() {
		return diagnostico;
	}

	public void setDiagnositco(String diagnositco) {
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

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}
	
	public List<String> getSintomas() {
		return sintomas;
	}

	public void setSintomas(List<String> sintomas) {
		this.sintomas = sintomas;
	}

	@Override
	public String toString() {
		return "ConsultaMedica [id=" + id + ", fecha=" + fecha + ", paciente=" + paciente + ", doctor=" + doctor
				+ ", peso=" + peso + ", temperatura=" + temperatura + ", diagnostico=" + diagnostico + ", medicacion="
				+ medicacion + ", observaciones=" + observaciones + "]";
	}

}
