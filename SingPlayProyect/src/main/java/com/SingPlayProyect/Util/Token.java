package com.SingPlayProyect.Util;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class Token {

	private final static Long TOKEN_DURACION = 3_600L;
	private final static String TOKEN_SECRETO = "cfdo8HGGrYlYiyyzp3GyMWGPz5q6CVvz";

	//https://it-tools.tech/token-generator?length=32

	public static String crearToken(String user, String email) {
		
		long expiracionTimepo = TOKEN_DURACION * 1_000L; /* TIEMPO EN MILISEGUNDOS */
		Date fechaCreacion = new Date(System.currentTimeMillis());
		Date expiracionFecha = new Date(System.currentTimeMillis() + expiracionTimepo);

		Map<String, Object> map = new HashMap<>();
		map.put("nombre", user);
		map.put("fecha_creacion", new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(fechaCreacion)); // esta es la fecha que aparecerá como texto en el token
		map.put("fecha_expiracion", new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(expiracionFecha)); // esta es la fecha que aparecerá como texto en el token

		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(fechaCreacion)
				.setExpiration(expiracionFecha)
				.addClaims(map)
				.signWith(Keys.hmacShaKeyFor(TOKEN_SECRETO.getBytes()))
				.compact();

	}
	
	
	
	
	public static UsernamePasswordAuthenticationToken getAuth(String token) {
		
		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(TOKEN_SECRETO.getBytes())
					.build()
					.parseClaimsJws(token)
					.getBody();
					
			String email = claims.getSubject();
			
			return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
		} catch (Exception e) {
			System.out.println("Error en el emtodo de validacion de token: " + e.getMessage());
			return null;
		}
		
		
	}
	
}
