package com.bolsaideas.springboot.form.app.services;

import java.util.List;

import com.bolsaideas.springboot.form.app.domain.Role;

public interface RolesService {
	
	public List<Role> listar();
	public Role obtenerPorId(Integer id);

}
