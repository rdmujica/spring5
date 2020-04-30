package com.bolsaideas.springboot.error.app.controllers;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bolsaideas.springboot.error.app.errors.UsuarioNoEncontradoException;

@ControllerAdvice
public class ErrorHandlerController {

	@ExceptionHandler(ArithmeticException.class)
	public String aritmeticaError(Exception ex, Model model) {
		model.addAttribute("error", "Error de Aritmética");
		model.addAttribute("message", ex.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());
		return "error/generica";		
	}
	
	@ExceptionHandler(NumberFormatException.class)
	public String numberFormatError(Exception ex, Model model) {
		model.addAttribute("error", "Error: Fomato número inválido!!");
		model.addAttribute("message", ex.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());
		return "error/generica";			
	}
	
	@ExceptionHandler(UsuarioNoEncontradoException.class)
	public String usuarioNoEncontradoError(Exception ex, Model model) {
		model.addAttribute("error", "Error: Usuario no encontrado!!");
		model.addAttribute("message", ex.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());
		return "error/generica";			
	}
}
