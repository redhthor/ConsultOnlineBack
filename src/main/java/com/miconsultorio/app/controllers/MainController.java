package com.miconsultorio.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainController {

	@GetMapping("consultonline/**")
	public String redireccion() {
		return "redirect:/templates/index.html";
	}
	
	@GetMapping
	public String start() {
		return "redirect:/templates/index.html";
	}
	
}
