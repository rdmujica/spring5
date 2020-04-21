package com.bolsaideas.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "redirect:/app/index";
	}
	
	@GetMapping("/forward")
	public String forward() {
		return "forward:/app/index";
	}
}
