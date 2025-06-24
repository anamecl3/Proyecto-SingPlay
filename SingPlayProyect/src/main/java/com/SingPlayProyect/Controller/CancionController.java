package com.SingPlayProyect.Controller;

import java.util.Map;


import com.SingPlayProyect.DTO.CancionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.SingPlayProyect.Model.CancionModel;
import com.SingPlayProyect.Service.CancionService;

@RestController
@CrossOrigin( origins ="https://localhost:4200")
@RequestMapping("/api/cancion")
public class CancionController {

	@Autowired
	private CancionService cancionService;

	// GET: http://localhost:8080/api/cancion/listar
	@GetMapping("/listar")
	public ResponseEntity<Map<String, Object>> listar() {
		return cancionService.listarCanciones();
	}

	// GET: http://localhost:8080/api/cancion/{id}
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> listarPorId(@PathVariable Integer id) {
		return cancionService.listarCancionPorId(id);
	}

	// POST: http://localhost:8080/api/cancion
	@PostMapping
	public ResponseEntity<Map<String, Object>> agregar(@Validated @RequestBody CancionDTO cancion) {
		return cancionService.agregarCancion(cancion);
	}

	// PUT: http://localhost:8080/api/cancion/{id}
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> editar(@RequestBody CancionDTO cancion, @PathVariable Integer id) {
		return cancionService.editarCancion(cancion, id);
	}

	// DELETE: http://localhost:8080/api/cancion/{id}
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer id) {
		return cancionService.eliminarCancion(id);
	}

	// PUT: http://localhost:8080/api/cancion/desactivar/{id}
	@PutMapping("/desactivar/{id}")
	public ResponseEntity<Map<String, Object>> desactivar(@PathVariable Integer id) {
		return cancionService.desactivarCancion(id);
	}

	// PUT: http://localhost:8080/api/cancion/activar/{id}
	@PutMapping("/activar/{id}")
	public ResponseEntity<Map<String, Object>> activar(@PathVariable Integer id) {
		return cancionService.activarCancion(id);
	}



}
