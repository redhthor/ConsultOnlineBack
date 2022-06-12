package com.miconsultorio.app.utils;

import org.owasp.esapi.ESAPI;

public class Sanitizer {
	
	private Sanitizer() {
		super();
	}
	
	public static String sanitizeHTML(String html) {
		return ESAPI.encoder().encodeForHTML(html);
	}
	
}
