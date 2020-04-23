package com.bolsadeideas.springboot.di.app.models.services;

import org.springframework.stereotype.Service;

//@Service("miServicioComplejo")
public class MiServicioComplejo implements IServicio {

	public String operacion() {
		return "ejecutando algun proceso importante Complejo...";
	}
}
