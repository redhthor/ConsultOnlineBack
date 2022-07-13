package com.miconsultorio.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("consultonline")
public class MainController {

	@GetMapping("/**")
	public String redireccion() {
		return "redirect:/templates/index.html";
	}
	
}
