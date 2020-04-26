package com.bolsaideas.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bolsaideas.springboot.form.app.services.RolesService;

@Component
public class RolesEditor extends PropertyEditorSupport {

	@Autowired
	private RolesService service;

	@Override
	public void setAsText(String idString) throws IllegalArgumentException {
		try {
			Integer id = Integer.parseInt(idString);
			setValue(service.obtenerPorId(id));
		} catch (NumberFormatException e) {
			setValue(null);
		}
	}

}
