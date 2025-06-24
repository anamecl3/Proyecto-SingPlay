package com.SingPlayProyect.Controller;

import java.util.Map;

import com.SingPlayProyect.DTO.AlbumDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.SingPlayProyect.Service.AlbumService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/album")
public class AlbumController {

	@Autowired
	private AlbumService albumService;

	// GET: http://localhost:8080/api/album/listar
	@GetMapping("/listar")
	public ResponseEntity<Map<String, Object>> listar() {
		return albumService.listarAlbumes();
	}

	// GET: http://localhost:8080/api/album/{id}
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> listarPorId(@PathVariable Integer id) {
		return albumService.listarAlbumPorId(id);
	}

	// POST: http://localhost:8080/api/album
	@PostMapping
	public ResponseEntity<Map<String, Object>> agregar(@Validated @RequestBody AlbumDTO album) {
		return albumService.agregarAlbum(album);
	}

	// PUT: http://localhost:8080/api/album/{id}
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> editar(@RequestBody AlbumDTO album, @PathVariable Integer id) {
		return albumService.editarAlbum(album, id);
	}

	// DELETE: http://localhost:8080/api/album/{id}
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer id) {
		return albumService.eliminarAlbum(id);
	}

	// PUT: http://localhost:8080/api/album/desactivar/{id}
	@PutMapping("/desactivar/{id}")
	public ResponseEntity<Map<String, Object>> desactivar(@PathVariable Integer id) {
		return albumService.desactivarAlbum(id);
	}

	// PUT: http://localhost:8080/api/album/activar/{id}
	@PutMapping("/activar/{id}")
	public ResponseEntity<Map<String, Object>> activar(@PathVariable Integer id) {
		return albumService.activarAlbum(id);
	}
}
