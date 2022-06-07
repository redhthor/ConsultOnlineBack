package com.miconsultorio.app.model.requestbody;

import java.util.Date;

public class RequestAgendaByDate {
	private Date fecha;
	private String idUsuario;
	
	public RequestAgendaByDate() {
		super();
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public String toString() {
		return "RequestAgendaByDate [fecha=" + fecha + ", idUsuario=" + idUsuario + "]";
	}	
	
}
