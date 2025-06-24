package com.SingPlayProyect.Controller;

import java.util.Map;

import com.SingPlayProyect.DTO.CantanteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.SingPlayProyect.Service.CantanteService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/cantante")
public class CantanteController {

	@Autowired
	private CantanteService cantanteService;

	// GET: http://localhost:8080/api/cantante/listar
	@GetMapping("/listar")
	public ResponseEntity<Map<String, Object>> listar() {
		return cantanteService.listarCantantes();
	}

	// GET: http://localhost:8080/api/cantante/{id}
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> listarPorId(@PathVariable Integer id) {
		return cantanteService.listarCantantesPorId(id);
	}

	// POST: http://localhost:8080/api/cantante
	@PostMapping
	public ResponseEntity<Map<String, Object>> agregar(@Validated @RequestBody CantanteDTO cantante) {
		return cantanteService.agregarCantante(cantante);
	}

	// PUT: http://localhost:8080/api/cantante/{id}
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> editar(@RequestBody CantanteDTO cantante, @PathVariable Integer id) {
		return cantanteService.editarCantante(cantante, id);
	}

	// DELETE: http://localhost:8080/api/cantante/{id}
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer id) {
		return cantanteService.eliminarCantante(id);
	}

	// PUT: http://localhost:8080/api/cantante/desactivar/{id}
	@PutMapping("/desactivar/{id}")
	public ResponseEntity<Map<String, Object>> desactivar(@PathVariable Integer id) {
		return cantanteService.desactivarCantante(id);
	}

	// PUT: http://localhost:8080/api/cantante/activar/{id}
	@PutMapping("/activar/{id}")
	public ResponseEntity<Map<String, Object>> activar(@PathVariable Integer id) {
		return cantanteService.activarCantante(id);
	}
}