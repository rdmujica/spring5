package com.bolsaideas.springboot.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsaideas.springboot.app.models.entity.Cliente;
import com.bolsaideas.springboot.app.models.service.IClienteService;
import com.bolsaideas.springboot.app.models.service.IUploadFileService;
import com.bolsaideas.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IUploadFileService uploadFileService;

	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable(value = "filename") String filename) {
		Resource recurso = null;
		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		//Cliente cliente = clienteService.findOne(id);
		Cliente cliente = clienteService.fetchByIdWidthFacturas(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "Elcliente no existe en la base de datos");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Detalle cliente: " + cliente.getNombre());
		return "ver";
	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageable = PageRequest.of(page, 5);
		Page<Cliente> clientes = this.clienteService.findAll(pageable);
		PageRender<Cliente> pageRender = new PageRender<Cliente>("/listar", clientes);
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		return "listar";
	}

	/*
	 * @RequestMapping(value = "/listar", method = RequestMethod.GET) public String
	 * listar(Model model) { model.addAttribute("titulo", "Listado de clientes");
	 * model.addAttribute("clientes", this.clienteService.findAll()); return
	 * "listar"; }
	 */
	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("titulo", "Formulario de Cliente");
		model.put("cliente", cliente);
		return "form";
	}

	@PostMapping("/form")
	public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}
		if (!foto.isEmpty()) {

			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
					&& cliente.getFoto().length() > 0) {
				uploadFileService.delete(cliente.getFoto());
			}

//			Path directorioRecursos = Paths.get("src//main//resources//static//uploads");
//			String rootPath = directorioRecursos.toFile().getAbsolutePath();
//			String rootPath = "//home//rafamujica//Documentos//workspace-curso1//uploads";

//				byte[] bytes = foto.getBytes();
//				Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
//				Files.write(rutaCompleta, bytes);

			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				e.printStackTrace();
			}
			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
			cliente.setFoto(uniqueFilename);
		}
		String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con éxito!!" : "Cliente creado con éxito!!";
		this.clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}

	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "E001: ID del Cliente no encontrado!");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "E002: ID del Cliente no encontrado!");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		return "form";
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		if (id > 0) {
			Cliente cliente = clienteService.findOne(id);
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito!");
			if (uploadFileService.delete(cliente.getFoto())) {
				flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + " eliminado con éxito!");
			}
		}
		return "redirect:/listar";
	}
}
