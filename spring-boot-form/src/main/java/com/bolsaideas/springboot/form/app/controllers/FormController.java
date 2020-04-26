package com.bolsaideas.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsaideas.springboot.form.app.domain.Pais;
import com.bolsaideas.springboot.form.app.domain.Role;
import com.bolsaideas.springboot.form.app.domain.Usuario;
import com.bolsaideas.springboot.form.app.editors.NombreMayusculaEditors;
import com.bolsaideas.springboot.form.app.editors.PaisPropertyEditor;
import com.bolsaideas.springboot.form.app.editors.RolesEditor;
import com.bolsaideas.springboot.form.app.services.PaisService;
import com.bolsaideas.springboot.form.app.services.RolesService;
import com.bolsaideas.springboot.form.app.validations.UsuarioValidador;

@Controller
@SessionAttributes("user")
public class FormController {

	@Autowired
	private UsuarioValidador validador;

	@Autowired
	private PaisService paisService;

	@Autowired
	private RolesService roleService;

	@Autowired
	private PaisPropertyEditor paisEditor;

	@Autowired
	private RolesEditor roleEditor;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validador);
		// Reemplaza la notacion @DateTimeFormat si la tiene
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		/*
		 * Todos los campos Date binder.registerCustomEditor(Date.class, new
		 * CustomDateEditor(dateFormat, false));
		 */

		// específico
		binder.registerCustomEditor(Date.class, "fechaDeCreacion", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditors());
		binder.registerCustomEditor(String.class, "email", new NombreMayusculaEditors());
		binder.registerCustomEditor(Pais.class, "pais", paisEditor);
		binder.registerCustomEditor(Role.class, "roles", roleEditor);
	}

	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario();
		usuario.setNombre("Jhon");
		usuario.setApellido("12.456.789-K");
		usuario.setIdentificador("12.456.789-K");
		usuario.setRut("12.456.789-K");
		usuario.setDireccion("12.456.789-K");
		usuario.setCuenta(5000);
		usuario.setEmail("rafa@gmail.com");
		usuario.setUsername("rdmujica");
		usuario.setPassword("123456");
		usuario.setPais(paisService.obtenerPorId(2));
		Date fecha = new Date();
		usuario.setFechaNacimiento(fecha);
		usuario.setFechaDeCreacion(new Date("2021/05/05"));
		usuario.setHabilitar(true);
		usuario.setValorSecreto("Algún valor secreto");
		// usuario.setPais(paisService.obtenerPorId(3));
		usuario.setPais(new Pais(2, "MX", "Mexico"));
		usuario.setRoles(Arrays.asList(new Role(2, "Usuario", "ROLE_USER"), new Role(3, "", "")));
		model.addAttribute("user", usuario);
		model.addAttribute("titulo", "Formulario");
		return "form";
	}

	/*
	 * Version 1
	 * 
	 * @PostMapping("/form") public String procesar(@Valid @ModelAttribute("user")
	 * Usuario usuario, BindingResult result, Model model, SessionStatus status) {
	 * // validador.validate(usuario, result); model.addAttribute("titulo",
	 * "Resultado del Form"); if (result.hasErrors()) { return "form"; } //
	 * model.addAttribute("usuario", usuario); status.setComplete(); return
	 * "resultado"; }
	 */

	@PostMapping("/form")
	public String procesar(@Valid @ModelAttribute("user") Usuario usuario, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Resultado del Form");
			return "form";
		}
		// model.addAttribute("usuario", usuario);

		return "redirect:/ver";
	}

	@GetMapping("/ver")
	public String ver(@SessionAttribute(name = "user", required = false) Usuario usuario, Model model,
			SessionStatus status) {
		if (usuario == null) {
			return "redirect:/form";
		}
		model.addAttribute("titulo", "Resultado del Form");
		status.setComplete();
		return "resultado";
	}

	@ModelAttribute("genero")
	public List<String> genero() {
		return Arrays.asList("Hombre", "Mujer");
	}

	@ModelAttribute("listaRolesString")
	public List<String> listaRolesString() {
		return Arrays.asList("ROLE_ADMIN", "ROLE_USER", "ROLE_MODERATOR");
	}

	@ModelAttribute("listaRolesMap")
	public Map<String, String> listaRolesMap() {
		Map<String, String> paises = new HashMap<String, String>();
		paises.put("ROLE_ADMIN", "Administrador");
		paises.put("ROLE_USER", "Usuario");
		paises.put("ROLE_MODERATOR", "Moderador");
		return paises;
	}

	@ModelAttribute("listaRolesObj")
	public List<Role> listaRolesObj() {
		return roleService.listar();
	}

	@ModelAttribute("paises")
	public List<String> paises() {
		return Arrays.asList("España", "Mexico", "Chile", "Argentina", "Perú", "Colombia", "Venezuela");
	}

	@ModelAttribute("paisesMap")
	public Map<String, String> paisesMap() {
		Map<String, String> paises = new HashMap<String, String>();
		paises.put("ES", "España");
		paises.put("MX", "Mexico");
		paises.put("CL", "Chile");
		paises.put("AR", "Argentina");
		paises.put("PE", "Perú");
		paises.put("CO", "Colombia");
		paises.put("VE", "Venezuela");
		return paises;
	}

	@ModelAttribute("listaPaises")
	public List<Pais> listaPaises() {
		return Arrays.asList(new Pais(1, "ES", "España"), new Pais(2, "MX", "Mexico"), new Pais(3, "CL", "Chile"),
				new Pais(4, "AR", "Argentina"), new Pais(5, "PE", "Perú"), new Pais(6, "CO", "Colombia"),
				new Pais(7, "VE", "Venezuela"));
	}

	@ModelAttribute("paisesList")
	public List<Pais> paisesList() {
		return paisService.listar();
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
