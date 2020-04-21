package com.bolsaideas.springboot.web.app.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bolsaideas.springboot.web.app.models.Usuario;

@Controller
@RequestMapping("/app")
public class IndexController {
	
	@Value("${texto.indexcontroller.index.titulo}")
	private String textoIndex;

	@ModelAttribute("usuarios")
	public List<Usuario> poblarUsuarios(){
		List<Usuario> usuarios = Arrays.asList(new Usuario("Rafael", "Mujica", "rafael@gmail.com"),
				new Usuario("Daniel", "Lopez", "daniel@gmail.com"), 
				new Usuario("Nany", "Medina", "nany@gmail.com"));
		return usuarios;
	}
	
	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de usuarios");
		return "listar";
	}
	
	/*
	 * @GetMapping("/listar") public String listar(Model model) { List<Usuario>
	 * usuarios = Arrays.asList(new Usuario("Rafael", "Mujica", "rafael@gmail.com"),
	 * new Usuario("Daniel", "Lopez", "daniel@gmail.com"), new Usuario("Nany",
	 * "Medina", "nany@gmail.com")); model.addAttribute("titulo",
	 * "Listado de usuarios"); model.addAttribute("usuarios", usuarios); return
	 * "listar"; }
	 */

	/*
	 * @GetMapping("/listar") public String listar(Model model) { List<Usuario>
	 * usuarios = new ArrayList<Usuario>(); usuarios.add(new
	 * Usuario("Rafael","Mujica", "rafael@gmail.com")); usuarios.add(new
	 * Usuario("Daniel","Lopez", "daniel@gmail.com")); usuarios.add(new
	 * Usuario("Nany","Medina", "n@gmail.com")); model.addAttribute("titulo",
	 * "Listado de usuarios"); model.addAttribute("usuarios", usuarios); return
	 * "listar"; }
	 */

	@RequestMapping("/perfil")
	public String perfil(Model model) {
		Usuario usuario = new Usuario();
		usuario.setNombre("Rafael");
		usuario.setApellido("Mujica");
		usuario.setEmail("rafael@gmail.com");
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Perfil del Usuario: ");
		return "perfil";
	}

//	  @RequestMapping(value="/",method = RequestMethod.GET)
	@GetMapping({ "/index", "/", "/home" })
	public String index(Model model) {
		model.addAttribute("titulo", textoIndex);
		return "index";
	}

//	@GetMapping({ "/index", "/", "/home" })
//	public String index(ModelMap model) {
//		model.addAttribute("titulo", "Hola spring Framework ModelMap!!");
//		return "index";
//	}

//	@GetMapping({ "/index", "/", "/home" })
//	public String index(Map<String, String> model) {
//		model.put("titulo", "Hola spring Framework Map!!");
//		return "index";
//	}

//	@GetMapping({ "/index", "/", "/home" })
//	public ModelAndView index(ModelAndView model) {
//		model.addObject("titulo", "Hola spring Framework ModelAndView!!");
//		model.setViewName("index");
//		return model;
//	}
}
