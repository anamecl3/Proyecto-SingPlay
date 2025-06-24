package com.SingPlayProyect.ServiceImplemnt;

import java.util.*;

import com.SingPlayProyect.DTO.CantanteDTO;
import com.SingPlayProyect.Model.AlbumModel;
import com.SingPlayProyect.Model.CantanteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.SingPlayProyect.Repository.CantanteRepository;
import com.SingPlayProyect.Service.CantanteService;

@Service
public class CantanteServiceImplement implements CantanteService {

	@Autowired
	private CantanteRepository cantanteRepository ;


	@Override
	public ResponseEntity<Map<String, Object>> listarCantantes() {
		Map<String, Object> respuesta = new HashMap<>();
		List<CantanteModel> lista = cantanteRepository.findAll();

		if (!lista.isEmpty()) {
			respuesta.put("mensaje", "Lista de cantantes");
			respuesta.put("fecha", new Date());
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("cantantes", lista);
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} else {
			respuesta.put("mensaje", "No existen registros de cantantes");
			respuesta.put("fecha", new Date());
			respuesta.put("status", HttpStatus.NOT_FOUND);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> listarCantantesPorId(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<CantanteModel> cantanteExiste = validarCantantePorId(id, respuesta);

		if (cantanteExiste.isPresent()) {
			respuesta.put("cantante", cantanteExiste.get());
			respuesta.put("mensaje", "Cantante encontrado");
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());
			return ResponseEntity.ok().body(respuesta);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> agregarCantante(CantanteDTO dto) {
		Map<String, Object> respuesta = new HashMap<>();
		try {

			Optional<CantanteModel> cantanteDuplicado = validarCantanteDuplicado(dto.getNombreCantante(), dto.getGeneroCantante(),respuesta);
			if (cantanteDuplicado.isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
			}

			CantanteModel cantante = new CantanteModel();
			cantante.setNombre(dto.getNombreCantante());
			cantante.setGenero(dto.getGeneroCantante());
			cantante.setImagenUrl(dto.getImagenUrlCantante());
			cantante.setDescripcion(dto.getDescripcionCantante());
			cantante.setActivo(true);

			cantanteRepository.save(cantante);
			respuesta.clear();
			respuesta.put("cantante", cantante);
			respuesta.put("mensaje", "Cantante añadido correctamente");
			respuesta.put("status", HttpStatus.CREATED);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);

		} catch (Exception e) {
			respuesta.put("mensaje", "Error al registrar el cantante: " + e.getMessage());
			respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> editarCantante(CantanteDTO dto, Integer id) {
		Map<String, Object> respuesta = new HashMap<>();

		try {
			Optional<CantanteModel> Cantante = validarCantantePorId(id, respuesta);
			if (Cantante.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}
			Optional<CantanteModel> cantanteDuplicado = validarCantanteDuplicado( dto.getNombreCantante(),dto.getGeneroCantante(), respuesta);
			if (cantanteDuplicado.isPresent() && !cantanteDuplicado.get().getIdCantante().equals(id)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
			}

			// Actualizar valores
			CantanteModel cantante = Cantante.get();
			cantante.setNombre(dto.getNombreCantante());
			cantante.setGenero(dto.getGeneroCantante());
			cantante.setImagenUrl(dto.getImagenUrlCantante());
			cantante.setDescripcion(dto.getDescripcionCantante());
			cantanteRepository.saveAndFlush(cantante);
			respuesta.clear();
			respuesta.put("cantante", cantante);
			respuesta.put("mensaje", "Cantante actualizado correctamente");
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);

		} catch (Exception e) {
			respuesta.put("mensaje", "Error al editar el cantante: " + e.getMessage());
			respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> eliminarCantante(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();

		Optional<CantanteModel> cantanteExiste = validarCantantePorId(id, respuesta);

		if (cantanteExiste.isPresent()) {
			cantanteRepository.delete(cantanteExiste.get());
			respuesta.put("mensaje", "Cantante eliminado correctamente");
			respuesta.put("status", HttpStatus.NO_CONTENT);//Eliminado sin contenido en la respuesta
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);//Eliminado sin contenido en la respuesta
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> desactivarCantante(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();

		Optional<CantanteModel> cantanteExistente = validarCantantePorId(id,respuesta);
		if (cantanteExistente.isPresent()) {
			CantanteModel cantante = cantanteExistente.get();
			cantante.setActivo(false);
			cantanteRepository.save(cantante);
			respuesta.put("mensaje", "Cantante desactivado correctamente");
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> activarCantante(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();

		Optional<CantanteModel> cantanteExistente = validarCantantePorId(id,respuesta);
		if (cantanteExistente.isPresent()) {
			CantanteModel cantante = cantanteExistente.get();
			cantante.setActivo(true);
			cantanteRepository.save(cantante);
			respuesta.put("mensaje", "Cantante activado correctamente");
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}


	private Optional<CantanteModel> validarCantantePorId(Integer idCantante, Map<String, Object> respuesta) {
		Optional<CantanteModel> cantanteOpt = cantanteRepository.findById(idCantante);

		if (cantanteOpt.isEmpty()) {
			respuesta.put("mensaje", "Cantante no encontrado con ID: " + idCantante);
			respuesta.put("status", HttpStatus.NOT_FOUND);
			respuesta.put("fecha", new Date());
			return Optional.empty();
		}

		return cantanteOpt;
	}

	private Optional<CantanteModel> validarCantanteDuplicado(String nombreCantante, String generoCantante,Map<String, Object> respuesta)  {

		Optional<CantanteModel> cantanteExistente = cantanteRepository.findByNombreAndGenero(nombreCantante,generoCantante);

		if (cantanteExistente.isPresent()) {
			respuesta.put("cantanteExistente", cantanteExistente);
			respuesta.put("mensaje", "Ya existe un cantante con el mismo nombre y genero.");
			respuesta.put("status", HttpStatus.CONFLICT);
			respuesta.put("fecha", new Date());
			return cantanteExistente;
		}

		return Optional.empty();
	}
}
