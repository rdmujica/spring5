package com.bolsaideas.springboot.app.auth.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.crypto.SecretKey;

import org.apache.logging.log4j.util.Base64Util;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.bolsaideas.springboot.app.auth.SimpleGrantedAuthorityMixin;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTServiceImpl implements IJWTService {
	
	public static final String SECRET=Base64Utils.encodeToString("Alguna.Clave.Secreta.0123456789.0123456789.0123456789.0123456789".getBytes());
	
	public static final long EXPIRATION_DATE=3600000L;
	
	public static final String TOKEN_PREFIX="Bearer ";

	@Override
	public String create(Authentication auth) throws IOException {
		String username = ((User) auth.getPrincipal()).getUsername();
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
		Claims claims=Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
		
		/*
		 * String token = Jwts.builder() .setSubject(username)
		 * .signWith(SignatureAlgorithm.HS512,
		 * "Alguna.Clave.Secreta.0123456789.0123456789.0123456789.0123456789".getBytes()
		 * ) .compact();
		 */
		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
		String token = Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.signWith(secretKey)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
				.compact();
		return token;
	}

	@Override
	public boolean validate(String token) {
		try {
			this.getClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public Claims getClaims(String token) {
		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
		Claims claims = Jwts
		.parser()
		.setSigningKey(SECRET.getBytes())
		.parseClaimsJws(resolve(token))
		.getBody();
		return claims;
	}

	@Override
	public String getUsername(String token) {
		return this.getClaims(token).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
		Object roles=this.getClaims(token).get("authorities");
		Collection<? extends GrantedAuthority> authorities = Arrays
				.asList(new ObjectMapper()
						.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
						.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
		return authorities;
	}

	@Override
	public String resolve(String token) {
		if(token!=null && token.startsWith(TOKEN_PREFIX)) {
			return token.replace(TOKEN_PREFIX, "");
		}
		return null;
	}

}
