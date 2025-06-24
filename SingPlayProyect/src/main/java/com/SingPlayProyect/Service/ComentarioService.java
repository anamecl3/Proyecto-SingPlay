package com.SingPlayProyect.Service;

import java.util.Map;

import com.SingPlayProyect.DTO.ComentarioDTO;
import org.springframework.http.ResponseEntity;

public interface ComentarioService {

	ResponseEntity<Map<String , Object>> listarComentarios();

	public ResponseEntity<Map<String, Object>> listarComentarioPorId(Integer id);

	public ResponseEntity<Map<String, Object>> agregarComentario(ComentarioDTO Comentario);

	public ResponseEntity<Map<String, Object>> editarComentario(ComentarioDTO Comentario, Integer id);

	public ResponseEntity<Map<String, Object>> eliminarComentario(Integer id);

}
