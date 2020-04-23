package com.bolsadeideas.springboot.di.app.models.services;

import org.springframework.stereotype.Service;

//@Service("miServicioSimple")
public class MiServicio implements IServicio {

	public String operacion() {
		return "ejecutando algun proceso importante simple...";
	}
}
