package com.miconsultorio.app.model.entities.bo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Medicacion {
	@NotBlank
	private String medicamento;
	@Min(value = 0)
	private Integer cantidad;
	private String unidadMedida;
	@Min(value = 0)
	private Integer frecuencia;
	private String periodoTiempo;
	
	public Medicacion() {
	
	}

	public Medicacion(String medicamento, Integer cantidad, String unidadMedida, Integer frecuencia,
			String periodoTiempo) {
		super();
		this.medicamento = medicamento;
		this.cantidad = cantidad;
		this.unidadMedida = unidadMedida;
		this.frecuencia = frecuencia;
		this.periodoTiempo = periodoTiempo;
	}

	public String getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(String medicamento) {
		this.medicamento = medicamento;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public Integer getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Integer frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getPeriodoTiempo() {
		return periodoTiempo;
	}

	public void setPeriodoTiempo(String periodoTiempo) {
		this.periodoTiempo = periodoTiempo;
	}

	@Override
	public String toString() {
		return "Medicacion [medicamento=" + medicamento + ", cantidad=" + cantidad + ", unidadMedida=" + unidadMedida
				+ ", frecuencia=" + frecuencia + ", periodoTiempo=" + periodoTiempo + "]";
	}
	
}
