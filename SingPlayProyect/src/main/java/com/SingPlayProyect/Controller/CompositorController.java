package com.SingPlayProyect.Controller;

import java.util.Map;

import com.SingPlayProyect.DTO.CompositorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.SingPlayProyect.Service.CompositorService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/compositor")
public class CompositorController {

	@Autowired
	private CompositorService compositorService;

	// GET: http://localhost:8080/api/compositor/listar
	@GetMapping("/listar")
	public ResponseEntity<Map<String, Object>> listar() {
		return compositorService.listarCompositores();
	}

	// GET: http://localhost:8080/api/compositor/{id}
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> listarPorId(@PathVariable Integer id) {
		return compositorService.listarCompositorPorId(id);
	}

	// POST: http://localhost:8080/api/compositor
	@PostMapping
	public ResponseEntity<Map<String, Object>> agregar(@Validated @RequestBody CompositorDTO dto) {
		return compositorService.agregarCompositor(dto);
	}

	// PUT: http://localhost:8080/api/compositor/{id}
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> editar(@Validated @RequestBody CompositorDTO dto, @PathVariable Integer id) {
		return compositorService.editarCompositor(dto, id);
	}

	// DELETE: http://localhost:8080/api/compositor/{id}
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer id) {
		return compositorService.eliminarCompositor(id);
	}

	// PUT: http://localhost:8080/api/compositor/desactivar/{id}
	@PutMapping("/desactivar/{id}")
	public ResponseEntity<Map<String, Object>> desactivar(@PathVariable Integer id) {
		return compositorService.desactivarCompositor(id);
	}

	// PUT: http://localhost:8080/api/compositor/activar/{id}
	@PutMapping("/activar/{id}")
	public ResponseEntity<Map<String, Object>> activar(@PathVariable Integer id) {
		return compositorService.activarCompositor(id);
	}
}