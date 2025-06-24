package com.SingPlayProyect.Service;

import java.util.Map;

import com.SingPlayProyect.DTO.UsuarioDTO;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {

	public ResponseEntity<Map<String, Object>> listarUsuarios();

	public ResponseEntity<Map<String, Object>> agregarUsuario(UsuarioDTO usuario);

}
