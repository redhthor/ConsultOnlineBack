package com.miconsultorio.app.model.requestbody;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.miconsultorio.app.model.entities.vo.PacienteVO;

public class RequestHistorialConsultas {
	private Date fecha;
	private PacienteVO paciente;
	@NotBlank
	private String usuario;
	@NotNull
	@Min(value = 1, message = "El valor mínimo debe ser 1")
	private Integer pagina;

	public RequestHistorialConsultas() {
		super();
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	@Override
	public String toString() {
		return "RequestHistorialConsultas [fecha=" + fecha + ", paciente=" + paciente + ", usuario=" + usuario
				+ ", pagina=" + pagina + "]";
	}

}
