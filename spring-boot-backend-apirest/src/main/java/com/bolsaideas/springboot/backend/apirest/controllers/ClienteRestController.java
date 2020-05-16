package com.bolsaideas.springboot.backend.apirest.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bolsaideas.springboot.backend.apirest.entity.Cliente;
import com.bolsaideas.springboot.backend.apirest.models.services.IClienteService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;

	@GetMapping("/clientes")
	public List<Cliente> index() {
		return this.clienteService.findAll();
	}

	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable(name = "id") Long id) {
		Cliente cliente = null;
		try {
			cliente = this.clienteService.findById(id);
		} catch (DataAccessException e) {
			String error = e.getMessage().concat(": ");
			return responseErrorEntity("Error al realizar la consulta en la base de datos", error,
					INTERNAL_SERVER_ERROR);
		}
		if (cliente == null) {
			return responseEntity("El cliente ID: ".concat(id.toString().concat(" no existe en la BD!")), NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente, OK);
	}

	@PostMapping("/clientes")
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
		Cliente clienteNuevo = null;
		if(result.hasErrors()) {
			/*
			 * List<String> errors = new ArrayList<>(); for(FieldError error:
			 * result.getFieldErrors()) { errors.add("El campo '"+ error.getField() +"' "+
			 * error.getDefaultMessage()); }
			 */
			
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(error -> "El campo '"+ error.getField() +"' "+ error.getDefaultMessage())
					.collect(Collectors.toList());			
			return new ResponseEntity<Map<String, Object>>(getMensaje("errors", errors) , BAD_REQUEST);			
		}		
		try {
			clienteNuevo = this.clienteService.save(cliente);
		} catch (DataAccessException e) {
			String error = e.getCause().getMessage().concat("---").concat(e.getMessage()).concat(": ");
			return responseErrorEntity("Error al realizar el insert en la base de datos", error, INTERNAL_SERVER_ERROR);
		}
		Map<String, Object> response = getMensaje("El cliente ha sido creado con éxito!!");
		response.put("cliente", clienteNuevo);
		return new ResponseEntity<Map<String, Object>>(response, CREATED);
	}

	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable(name = "id") Long id) {
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(error -> "El campo '" + error.getField() + "' "+ error.getDefaultMessage())
					.collect(Collectors.toList());
			return new ResponseEntity<Map<String, Object>>(getMensaje("errors", errors) , BAD_REQUEST);						
		}
		
		Cliente clienteActual = this.clienteService.findById(id);
		if (clienteActual == null) {
			return responseEntity(
					"Error, no se puede editar, el cliente ID: ".concat(id.toString().concat(" no existe en la BD!")),
					NOT_FOUND);
		}
		Cliente clienteUpdated = null;
		try {
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setCreateAt(cliente.getCreateAt());
			clienteUpdated = this.clienteService.save(clienteActual);
		} catch (DataAccessException e) {
			String error = e.getMessage().concat(": ");
			return responseErrorEntity("Error al realizar la actualizacion en la base de datos", error,
					INTERNAL_SERVER_ERROR);
		}
		Map<String, Object> response = getMensaje("El cliente ha sido actualizado con éxito!!");
		response.put("cliente", clienteUpdated);
		return new ResponseEntity<Map<String, Object>>(response, CREATED);
	}

	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			this.clienteService.delete(id);
		} catch (DataAccessException e) {
			String error = e.getMessage().concat(": ");
			return responseErrorEntity("Error al eliminar la actualizacion en la base de datos", error,
					INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(getMensaje("El cliente ha sido eliminado con éxito!!"), OK);
	}

	private static Map<String, Object> getMensaje(String mensaje) {
		return getMensaje("mensaje", mensaje);
	}

	private static Map<String, Object> getMensaje(String key, Object mensaje) {
		Map<String, Object> response = new HashMap<>();
		response.put(key, mensaje);
		return response;
	}

	private static ResponseEntity<Map<String, Object>> responseEntity(String mensaje, HttpStatus status) {
		return new ResponseEntity<Map<String, Object>>(getMensaje(mensaje), status);
	}

	private static ResponseEntity<Map<String, Object>> responseErrorEntity(String mensaje, String error,
			HttpStatus status) {
		Map<String, Object> response = getMensaje(mensaje);
		response.put("error", error);
		return new ResponseEntity<Map<String, Object>>(response, status);
	}
}
