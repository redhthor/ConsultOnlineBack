package com.miconsultorio.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiConsultorioApp {

	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.keyStore", "C:\\Program Files\\Java\\jdk1.8.0_241\\jre\\lib\\security\\keystore.pkcs12");
		System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
		SpringApplication.run(MiConsultorioApp.class, args);
	}

}
