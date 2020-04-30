package com.bolsaideas.springboot.error.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bolsaideas.springboot.error.app.errors.UsuarioNoEncontradoException;
import com.bolsaideas.springboot.error.app.models.domain.Usuario;
import com.bolsaideas.springboot.error.app.services.UsuarioService;

@Controller
public class AppController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/index")
	private String index(Model model) {
		@SuppressWarnings("unused")
		// int valor=100/0;
		Integer valor = Integer.parseInt("10x");
		return "index";
	}

	@GetMapping("/ver/{id}")
	private String ver(@PathVariable Integer id, Model model) {
		/*
		 * Usuario usuario = usuarioService.obtenerPorId(id);
		 * model.addAttribute("usuario", usuario); if (usuario == null) { throw new
		 * UsuarioNoEncontradoException(id.toString()); }
		 */
		Usuario usuario = usuarioService.obtenerPorIdOptional(id)
				.orElseThrow(() -> new UsuarioNoEncontradoException(id.toString()));
		model.addAttribute("titulo", "Detalle usuario: ".concat(usuario.getNombre()));
		return "ver";
	}

}
