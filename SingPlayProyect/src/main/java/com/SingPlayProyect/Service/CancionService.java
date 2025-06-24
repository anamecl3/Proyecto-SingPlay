package com.SingPlayProyect.Service;

import java.util.Map;

import com.SingPlayProyect.DTO.CancionDTO;
import org.springframework.http.ResponseEntity;

public interface CancionService {


	public ResponseEntity<Map<String, Object>> listarCanciones();

	public ResponseEntity<Map<String, Object>> listarCancionPorId(Integer id);

	public ResponseEntity<Map<String, Object>> agregarCancion(CancionDTO cancion);

	public ResponseEntity<Map<String, Object>> editarCancion(CancionDTO cancion, Integer id);

	public ResponseEntity<Map<String, Object>> eliminarCancion(Integer id);

	public ResponseEntity<Map<String, Object>> desactivarCancion(Integer id);

	public ResponseEntity<Map<String, Object>> activarCancion(Integer id);

}
