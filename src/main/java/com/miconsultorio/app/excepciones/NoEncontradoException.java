package com.miconsultorio.app.excepciones;

public class NoEncontradoException extends Exception {
	private static final long serialVersionUID = 654L;
	
	public NoEncontradoException(String mensaje) {
		super(mensaje);
	}
	
}
