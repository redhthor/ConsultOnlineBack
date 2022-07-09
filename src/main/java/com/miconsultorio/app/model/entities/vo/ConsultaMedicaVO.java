package com.miconsultorio.app.model.entities.vo;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.miconsultorio.app.model.entities.ConsultaMedica;
import com.miconsultorio.app.model.entities.bo.Medicacion;

public class ConsultaMedicaVO {
	private String id;
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date fecha;
	@Valid
	private PacienteVO paciente;
	@NotBlank
	private String doctor;
	@NotNull
	@Min(value = 1)
	private Float peso;
	@NotNull
	@Min(value = 1)
	private Float estatura;
	@NotNull
	@Min(value = 20)
	private Float temperatura;
	@NotNull
	private String diagnostico;
	@Valid
	private List<Medicacion> medicacion;
	private String presion;
	private List<String> sintomas;
	private String observaciones;

	public ConsultaMedicaVO() {
		super();
	}

	public ConsultaMedica toEntity() {
		ConsultaMedica cm = new ConsultaMedica();
		cm.setId(this.id);
		cm.setDiagnostico(this.diagnostico);
		cm.setDoctor(this.doctor);
		cm.setFecha(this.fecha);
		cm.setMedicacion(this.medicacion);
		cm.setObservaciones(this.observaciones);
		cm.setPaciente(this.paciente.toEntity());
		cm.setPeso(this.peso);
		cm.setTemperatura(this.temperatura);
		cm.setEstatura(estatura);
		cm.setSintomas(sintomas);
		cm.setPresion(presion);
		return cm;
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

	public PacienteVO getPaciente() {
		return paciente;
	}

	public void setPaciente(PacienteVO paciente) {
		this.paciente = paciente;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
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
		return "ConsultaMedicaVO [id=" + id + ", fecha=" + fecha + ", paciente=" + paciente + ", doctor=" + doctor
				+ ", peso=" + peso + ", estatura=" + estatura + ", temperatura=" + temperatura + ", diagnostico="
				+ diagnostico + ", medicacion=" + medicacion + ", presion=" + presion + ", sintomas=" + sintomas
				+ ", observaciones=" + observaciones + "]";
	}


}
