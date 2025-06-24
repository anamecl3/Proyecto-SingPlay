package com.SingPlayProyect.ServiceImplemnt;

import java.util.*;

import com.SingPlayProyect.DTO.CompositorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.SingPlayProyect.Model.CompositorModel;
import com.SingPlayProyect.Repository.CompositorRepository;
import com.SingPlayProyect.Service.CompositorService;


@Service
public class CompositorServiceImplement implements CompositorService {

	@Autowired
	private CompositorRepository compositorRepository;

	@Override
	public ResponseEntity<Map<String, Object>> listarCompositores() {
		Map<String, Object> respuesta = new HashMap<>();
		List<CompositorModel> lista = compositorRepository.findAll();

		if (!lista.isEmpty()) {
			respuesta.put("mensaje", "Lista de compositores");
			respuesta.put("fecha", new Date());
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("compositores", lista);
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} else {
			respuesta.put("mensaje", "No existen registros de compositores");
			respuesta.put("fecha", new Date());
			respuesta.put("status", HttpStatus.NOT_FOUND);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> listarCompositorPorId(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<CompositorModel> compositorExiste = validarCompositorPorId(id, respuesta);

		if (compositorExiste.isPresent()) {
			respuesta.put("compositor", compositorExiste.get());
			respuesta.put("mensaje", "Compositor encontrado");
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());
			return ResponseEntity.ok().body(respuesta);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> agregarCompositor(CompositorDTO dto) {
		Map<String, Object> respuesta = new HashMap<>();
		try {

			Optional<CompositorModel> compositorDuplicado = validarCompositorDuplicado(dto.getNombreCompositor(),respuesta);
			if (compositorDuplicado.isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
			}

			CompositorModel compositor = new CompositorModel();
			compositor.setNombre(dto.getNombreCompositor());
			compositor.setNacionalidad(dto.getNacionalidadCompositor());
			compositor.setImagenUrl(dto.getImagenUrlCompositor());
			compositor.setDescripcion(dto.getDescripcionCompositor());
			compositor.setActivo(true);

			compositorRepository.save(compositor);
			respuesta.clear();
			respuesta.put("compositor", compositor);
			respuesta.put("mensaje", "Compositor añadido correctamente");
			respuesta.put("status", HttpStatus.CREATED);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);

		} catch (Exception e) {
			respuesta.put("mensaje", "Error al registrar el compositor: " + e.getMessage());
			respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> editarCompositor(CompositorDTO dto, Integer id) {
		Map<String, Object> respuesta = new HashMap<>();

		try {
			Optional<CompositorModel> Compositor = validarCompositorPorId(id, respuesta);
			if (Compositor.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}
			Optional<CompositorModel> compositorDuplicado = validarCompositorDuplicado( dto.getNombreCompositor(), respuesta);
			if (compositorDuplicado.isPresent() && !compositorDuplicado.get().getIdCompositor().equals(id)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
			}

			// Actualizar valores
			CompositorModel compositor = Compositor.get();
			compositor.setNombre(dto.getNombreCompositor());
			compositor.setNacionalidad(dto.getNacionalidadCompositor());
			compositor.setImagenUrl(dto.getImagenUrlCompositor());
			compositor.setDescripcion(dto.getDescripcionCompositor());
			compositorRepository.saveAndFlush(compositor);
			respuesta.clear();
			respuesta.put("compositor", compositor);
			respuesta.put("mensaje", "Compositor actualizado correctamente");
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);

		} catch (Exception e) {
			respuesta.put("mensaje", "Error al editar el compositor: " + e.getMessage());
			respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> eliminarCompositor(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();

		Optional<CompositorModel> compositorExiste = validarCompositorPorId(id, respuesta);

		if (compositorExiste.isPresent()) {
			compositorRepository.delete(compositorExiste.get());
			respuesta.put("mensaje", "Compositor eliminado correctamente");
			respuesta.put("status", HttpStatus.NO_CONTENT);//Eliminado sin contenido en la respuesta
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);//Eliminado sin contenido en la respuesta
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> desactivarCompositor(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();

		Optional<CompositorModel> compositorExistente = validarCompositorPorId(id,respuesta);
		if (compositorExistente.isPresent()) {
			CompositorModel compositor = compositorExistente.get();
			compositor.setActivo(false);
			compositorRepository.save(compositor);
			respuesta.put("mensaje", "Compositor desactivado correctamente");
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> activarCompositor(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();

		Optional<CompositorModel> compositorExistente = validarCompositorPorId(id,respuesta);
		if (compositorExistente.isPresent()) {
			CompositorModel compositor = compositorExistente.get();
			compositor.setActivo(true);
			compositorRepository.save(compositor);
			respuesta.put("mensaje", "Compositor activado correctamente");
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}


	private Optional<CompositorModel> validarCompositorPorId(Integer idCompositor, Map<String, Object> respuesta) {
		Optional<CompositorModel> compositorOpt = compositorRepository.findById(idCompositor);

		if (compositorOpt.isEmpty()) {
			respuesta.put("mensaje", "Compositor no encontrado con ID: " + idCompositor);
			respuesta.put("status", HttpStatus.NOT_FOUND);
			respuesta.put("fecha", new Date());
			return Optional.empty();
		}

		return compositorOpt;
	}

	private Optional<CompositorModel> validarCompositorDuplicado(String nombreCompositor,Map<String, Object> respuesta)  {

		Optional<CompositorModel> compositorExistente = compositorRepository.findByNombre(nombreCompositor);

		if (compositorExistente.isPresent()) {
			respuesta.put("compositorExistente", compositorExistente);
			respuesta.put("mensaje", "Ya existe un compositor con el mismo nombre.");
			respuesta.put("status", HttpStatus.CONFLICT);
			respuesta.put("fecha", new Date());
			return compositorExistente;
		}

		return Optional.empty();
	}
}