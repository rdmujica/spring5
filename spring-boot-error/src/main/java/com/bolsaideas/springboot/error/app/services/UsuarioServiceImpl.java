package com.bolsaideas.springboot.error.app.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bolsaideas.springboot.error.app.models.domain.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private List<Usuario> lista;

	public UsuarioServiceImpl() {
		lista = Arrays.asList(new Usuario(1, "Rafael", "Mujica"), new Usuario(2, "Daniel", "Lopez"),
				new Usuario(3, "Samuel", "Mujica"));

	}

	@Override
	public List<Usuario> listar() {
		return this.lista;
	}

	@Override
	public Usuario obtenerPorId(Integer id) {
		Usuario resultado = null;
		for (Usuario u : this.lista) {
			if (u.getId().equals(id)) {
				resultado = u;
				break;
			}
		}
		return resultado;
	}

	@Override
	public Optional<Usuario> obtenerPorIdOptional(Integer id) {
		Usuario usuario=this.obtenerPorId(id);
		return Optional.ofNullable(usuario);
	}

}
