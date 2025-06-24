package com.SingPlayProyect.Service;

import java.util.Map;

import com.SingPlayProyect.DTO.CantanteDTO;
import org.springframework.http.ResponseEntity;

public interface CantanteService {
	
	public ResponseEntity<Map<String , Object>> listarCantantes();

	public ResponseEntity<Map<String, Object>> listarCantantesPorId(Integer id);

	public ResponseEntity<Map<String, Object>> agregarCantante(CantanteDTO Cantante);

	public ResponseEntity<Map<String, Object>> editarCantante(CantanteDTO Cantante, Integer id);

	public ResponseEntity<Map<String, Object>> eliminarCantante(Integer id);

	public ResponseEntity<Map<String, Object>> desactivarCantante(Integer id);

	public ResponseEntity<Map<String, Object>> activarCantante(Integer id);
	
}
