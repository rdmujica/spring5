package com.bolsaideas.springboot.reactor.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bolsaideas.springboot.reactor.app.models.Usuario;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		List<String> usuariosList= Arrays.asList(
				"Rafael Mujica"
				, "Daniel Lopez"
				, "Samuel Mujica"
				, "Don Algo"
				, "Diego Linares"
				, "Dario Mujica"
				,"Bruce Lee","Bruce Willis"
				);
		
		
		//Flux<String> nombres = Flux.just("Rafael Mujica", "Daniel Lopez", "Samuel Mujica", "Don Algo", "Diego Linares", "Dario Mujica","Bruce Lee","Bruce Willis");
		Flux<String> nombres = Flux.fromIterable(usuariosList);
		Flux<Usuario> usuarios = nombres
				.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase()
											,nombre.split(" ")[1].toUpperCase())
				)
				.filter(usuario -> usuario.getNombre().equalsIgnoreCase("bruce"))
				.doOnNext(usuario -> {
					if (usuario == null) {
						throw new RuntimeException("Nombres no pueden ser vacíos");
					}
					System.out.println(usuario.toString());
				})
				.map(usuario -> {
					String nombre=usuario.getNombre();
					usuario.setNombre(nombre.toLowerCase());
					return usuario;
					});

			usuarios.subscribe(e -> log.info(e.toString()), error -> log.error(error.getMessage()), new Runnable() {
			@Override
			public void run() {
				log.info("Ha finalizado la ejecución del observable con éxito");
			}
		});
	}

}
