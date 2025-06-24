package com.SingPlayProyect.Controller;

import java.util.Map;

import com.SingPlayProyect.DTO.ComentarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.SingPlayProyect.Service.ComentarioService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/comentario")
public class ComentarioController {

	@Autowired
	private ComentarioService comentarioService;

	// GET: http://localhost:8080/api/comentario/listar
	@GetMapping("/listar")
	public ResponseEntity<Map<String, Object>> listar() {
		return comentarioService.listarComentarios();
	}

	// GET: http://localhost:8080/api/comentario/{id}
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> listarPorId(@PathVariable Integer id) {
		return comentarioService.listarComentarioPorId(id);
	}

	// POST: http://localhost:8080/api/comentario
	@PostMapping
	public ResponseEntity<Map<String, Object>> agregar(@Validated @RequestBody ComentarioDTO comentario) {
		return comentarioService.agregarComentario(comentario);
	}

	// PUT: http://localhost:8080/api/comentario/{id}
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> editar(@RequestBody ComentarioDTO comentario, @PathVariable Integer id) {
		return comentarioService.editarComentario(comentario, id);
	}

	// DELETE: http://localhost:8080/api/comentario/{id}
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer id) {
		return comentarioService.eliminarComentario(id);
	}

}