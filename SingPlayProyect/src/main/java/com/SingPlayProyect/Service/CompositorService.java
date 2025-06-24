package com.SingPlayProyect.Service;

import java.util.Map;

import com.SingPlayProyect.DTO.CompositorDTO;
import org.springframework.http.ResponseEntity;

public interface CompositorService {

	public ResponseEntity<Map<String , Object>> listarCompositores();

	public ResponseEntity<Map<String, Object>> listarCompositorPorId(Integer id);

	public ResponseEntity<Map<String, Object>> agregarCompositor(CompositorDTO Compositor);

	public ResponseEntity<Map<String, Object>> editarCompositor(CompositorDTO Compositor, Integer id);

	public ResponseEntity<Map<String, Object>> eliminarCompositor(Integer id);

	public ResponseEntity<Map<String, Object>> desactivarCompositor(Integer id);

	public ResponseEntity<Map<String, Object>> activarCompositor(Integer id);
}
