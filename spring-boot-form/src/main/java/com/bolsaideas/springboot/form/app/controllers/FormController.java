package com.bolsaideas.springboot.form.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsaideas.springboot.form.app.domain.Usuario;
import com.bolsaideas.springboot.form.app.validations.UsuarioValidador;

@Controller
@SessionAttributes("user")
public class FormController {
	
	@Autowired
	private UsuarioValidador validador;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validador);
	}

	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario();
		usuario.setNombre("Jhon");
		usuario.setApellido("Doe");
		usuario.setIdentificador("12.456.789-K");
		model.addAttribute("user", usuario);
		model.addAttribute("titulo", "Formulario");
		return "form";
	}
	
	@PostMapping("/form")
	public String procesar(@Valid @ModelAttribute("user") Usuario usuario, BindingResult result, Model model, SessionStatus status) {
		//validador.validate(usuario, result);
		model.addAttribute("titulo", "Resultado del Form");
		if (result.hasErrors()) {
			return "form";
		}
		//model.addAttribute("usuario", usuario);
		status.setComplete();
		return "resultado";
	}

	/*
	 * @PostMapping("/form") public String procesar(@Valid @ModelAttribute("user")
	 * Usuario usuario, BindingResult result, Model model) {
	 * model.addAttribute("titulo", "Resultado del Form"); if (result.hasErrors()) {
	 * Map<String, String> errores = new HashMap<>();
	 * result.getFieldErrors().forEach(err -> { errores.put(err.getField(),
	 * "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()
	 * )); }); model.addAttribute("error", errores); return "form"; }
	 * //model.addAttribute("usuario", usuario); return "resultado"; }
	 */
	/*
	 * @PostMapping("/form") public String procesar(Model model,
	 * 
	 * @RequestParam(name="username") String username,
	 * 
	 * @RequestParam String password,
	 * 
	 * @RequestParam String email) { Usuario usuario=new Usuario();
	 * usuario.setEmail(email); usuario.setPassword(password);
	 * usuario.setUsername(username); model.addAttribute("usuario", usuario);
	 * model.addAttribute("titulo", "Resultado del Form"); return "resultado"; }
	 */
}
