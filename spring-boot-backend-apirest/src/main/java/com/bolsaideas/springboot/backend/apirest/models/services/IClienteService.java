package com.bolsaideas.springboot.backend.apirest.models.services;

import java.util.List;

import com.bolsaideas.springboot.backend.apirest.entity.Cliente;

public interface IClienteService {

	public List<Cliente> findAll();

}
