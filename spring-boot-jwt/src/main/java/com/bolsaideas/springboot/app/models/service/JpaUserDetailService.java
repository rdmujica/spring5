package com.bolsaideas.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsaideas.springboot.app.models.entity.Role;
import com.bolsaideas.springboot.app.models.entity.Usuario;
import com.bolsaideas.springboot.app.models.entity.dao.IUsuarioDao;

@Service("jpaUserDetailService")
public class JpaUserDetailService implements UserDetailsService {
	
	private final Logger logger= LoggerFactory.getLogger(JpaUserDetailService.class);

	@Autowired
	IUsuarioDao usuarioDao;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByUsername(username);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : usuario.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}	
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

}
