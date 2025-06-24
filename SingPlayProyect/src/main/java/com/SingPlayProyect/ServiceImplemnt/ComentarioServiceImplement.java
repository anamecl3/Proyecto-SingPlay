package com.SingPlayProyect.ServiceImplemnt;

import java.time.LocalDateTime;
import java.util.*;

import com.SingPlayProyect.DTO.ComentarioDTO;
import com.SingPlayProyect.Model.*;
import com.SingPlayProyect.Repository.CancionRepository;
import com.SingPlayProyect.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.SingPlayProyect.Repository.ComentarioRepository;
import com.SingPlayProyect.Service.ComentarioService;


@Service
public class ComentarioServiceImplement implements ComentarioService{

	@Autowired
	private ComentarioRepository comentariorepository ;
	@Autowired
	private CancionRepository cancionRepository ;
	@Autowired
	private UsuarioRepository usuarioRepository ;
	@Override
	public ResponseEntity<Map<String, Object>> listarComentarios() {
		Map<String, Object> respuesta = new HashMap<>();
		List<ComentarioModel> comentario = comentariorepository.findAll();

		if (!comentario.isEmpty()) {
			respuesta.put("mensaje", "Lista de comentarios");
			respuesta.put("fecha", new Date());
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("comentarios", comentario);
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} else {
			respuesta.put("mensaje", "No existen registros");
			respuesta.put("fecha", new Date());
			respuesta.put("status", HttpStatus.NOT_FOUND);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> listarComentarioPorId(Integer idComentario) {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<ComentarioModel> comentarioExiste = validarComentarioPorId(idComentario,respuesta);

		if (comentarioExiste.isPresent()) {
			respuesta.put("comentario", comentarioExiste.get());
			respuesta.put("mensaje", "Búsqueda correcta");
			respuesta.put("fecha", new Date());
			respuesta.put("status", HttpStatus.OK);
			return ResponseEntity.ok().body(respuesta);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> agregarComentario(ComentarioDTO dto) {
		Map<String, Object> respuesta = new HashMap<>();

		try {

			Optional<CancionModel> Cancion = validarCancionPorId(dto.getIdCancion(), respuesta);
			if (Cancion.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}

			Optional<UsuarioModel> Usuario = validarUsuarioPorId(dto.getIdUsuario(), respuesta);
			if (Usuario.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}

			Optional<ComentarioModel> comentarioDuplicado = validarComentarioDuplicado(dto.getContenido(),dto.getIdUsuario(),dto.getIdCancion(), respuesta);
			if (comentarioDuplicado.isPresent() && dto.getContenido().equals(comentarioDuplicado.get().getContenido())) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
			}

			ComentarioModel comentario = new ComentarioModel();
			comentario.setContenido(dto.getContenido());
			comentario.setFecha(LocalDateTime.now());
			comentario.setUsuario(Usuario.get());
			comentario.setCancion(Cancion.get());
			comentariorepository.save(comentario);

			respuesta.clear();
			respuesta.put("comentario", comentario);
			respuesta.put("mensaje", "Comentario añadida correctamente");
			respuesta.put("status", HttpStatus.CREATED);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);

		} catch (Exception e) {
			respuesta.put("mensaje", "Error al registrar el comentario: " + e.getMessage());
			respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> editarComentario(ComentarioDTO dto, Integer id) {
		Map<String, Object> respuesta = new HashMap<>();

		try {
			Optional<ComentarioModel> Comentario = validarComentarioPorId(id, respuesta);
			if (Comentario.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}

			Optional<CancionModel> Cancion = validarCancionPorId(dto.getIdCancion(), respuesta);
			if (Cancion.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}

			Optional<UsuarioModel> Usuario = validarUsuarioPorId(dto.getIdUsuario(), respuesta);
			if (Usuario.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}

			Optional<ComentarioModel> comentarioDuplicado = validarComentarioDuplicado(dto.getContenido(),dto.getIdUsuario(),dto.getIdCancion(), respuesta);
			if (comentarioDuplicado.isPresent() &&  dto.getContenido().equals(comentarioDuplicado.get().getContenido())) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
			}

			ComentarioModel comentario = Comentario.get();
			comentario.setContenido(dto.getContenido());
			comentario.setFecha(LocalDateTime.now());
			comentario.setUsuario(Usuario.get());
			comentario.setCancion(Cancion.get());
			comentariorepository.saveAndFlush(comentario);

			respuesta.clear();
			respuesta.put("comentario", comentario);
			respuesta.put("mensaje", "Comentario actualizado correctamente");
			respuesta.put("status", HttpStatus.CREATED);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);

		} catch (Exception e) {
			respuesta.put("mensaje", "Error al registrar el comentario: " + e.getMessage());
			respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
	@Override
	public ResponseEntity<Map<String, Object>> eliminarComentario(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<ComentarioModel> comentarioExistente = validarComentarioPorId(id, respuesta);

		if (comentarioExistente.isPresent()) {
			comentariorepository.delete(comentarioExistente.get());
			respuesta.put("mensaje", "Comentario eliminado correctamente");
			respuesta.put("fecha", new Date());
			respuesta.put("status", HttpStatus.OK);
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}


	private Optional<ComentarioModel> validarComentarioPorId(Integer idComentario, Map<String, Object> respuesta) {
		Optional<ComentarioModel> comentarioOpt = comentariorepository.findById(idComentario);

		if (comentarioOpt.isEmpty()) {
			respuesta.put("mensaje", "Comentario no encontrado con ID: " + idComentario);
			respuesta.put("status", HttpStatus.NOT_FOUND);
			respuesta.put("fecha", new Date());
			return Optional.empty();
		}
		return comentarioOpt;
	}
	private Optional<CancionModel> validarCancionPorId(Integer idCancion, Map<String, Object> respuesta) {

		Optional<CancionModel> cancionOpt = cancionRepository.findById(idCancion);

		if (cancionOpt.isEmpty()) {
			respuesta.put("mensaje", "Canción no encontrado con ID: " + idCancion);
			respuesta.put("status", HttpStatus.NOT_FOUND);
			respuesta.put("fecha", new Date());
			return Optional.empty();
		}

		return cancionOpt;
	}

	private Optional<UsuarioModel> validarUsuarioPorId(Integer idUsuario, Map<String, Object> respuesta) {

		Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(idUsuario);

		if (usuarioOpt.isEmpty()) {
			respuesta.put("mensaje", "Usuario no encontrado con ID: " + idUsuario);
			respuesta.put("status", HttpStatus.NOT_FOUND);
			respuesta.put("fecha", new Date());
			return Optional.empty();
		}

		return usuarioOpt;
	}
	private Optional<ComentarioModel> validarComentarioDuplicado(String contenido,Integer idUsuario,Integer idCancion, Map<String, Object> respuesta)  {

		Optional<ComentarioModel> comentarioExistente = comentariorepository.findByContenidoAndUsuario_IdUsuarioAndCancion_IdCancion(contenido,idUsuario,idCancion);
		if (comentarioExistente.isPresent()) {
			respuesta.put("comentarioExistente", comentarioExistente);
			respuesta.put("mensaje", "Ya existe un comentario similar en la canción del usuario .");
			respuesta.put("status", HttpStatus.CONFLICT);
			respuesta.put("fecha", new Date());
			return comentarioExistente;
		}

		return Optional.empty();
	}
}
