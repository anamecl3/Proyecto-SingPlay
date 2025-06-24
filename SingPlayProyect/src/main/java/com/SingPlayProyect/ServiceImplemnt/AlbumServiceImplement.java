package com.SingPlayProyect.ServiceImplemnt;

import java.util.*;

import com.SingPlayProyect.DTO.AlbumDTO;
import com.SingPlayProyect.Model.CantanteModel;
import com.SingPlayProyect.Repository.CantanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.SingPlayProyect.Model.AlbumModel;
import com.SingPlayProyect.Repository.AlbumRepository;
import com.SingPlayProyect.Service.AlbumService;


@Service
public class AlbumServiceImplement implements AlbumService{

	@Autowired
	private AlbumRepository albumRepository;
	@Autowired
	private CantanteRepository cantanteRepository;
	@Override
	public ResponseEntity<Map<String, Object>> listarAlbumes() {
		Map<String, Object> respuesta = new HashMap<>();
		List<AlbumModel> albums = albumRepository.findAll();

		if (!albums.isEmpty()) {
			respuesta.put("mensaje", "Lista de álbumes");
			respuesta.put("fecha", new Date());
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("album", albums);
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} else {
			respuesta.put("mensaje", "No existen registros");
			respuesta.put("fecha", new Date());
			respuesta.put("status", HttpStatus.NOT_FOUND);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> listarAlbumPorId(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<AlbumModel> albumExiste = validarAlbumPorId(id, respuesta);

		if (albumExiste.isPresent()) {
			respuesta.put("album", albumExiste.get());
			respuesta.put("mensaje", "Álbum encontrado");
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());
			return ResponseEntity.ok().body(respuesta);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> agregarAlbum(AlbumDTO dto) {
		Map<String, Object> respuesta = new HashMap<>();

		try {

			Optional<CantanteModel> Cantante = validarCantantePorId(dto.getIdCantante(), respuesta);
			if (Cantante.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}

			Optional<AlbumModel> albumDuplicado = validarAlbumDuplicado(dto.getTitulo(), dto.getIdCantante(),respuesta);
			if (albumDuplicado.isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
			}
			
			AlbumModel album = new AlbumModel();
			album.setTitulo(dto.getTitulo());
			album.setFechaLanzamiento(dto.getFechaLanzamiento());
			album.setImagenUrl(dto.getImagenUrl());
			album.setActivo(true);
			album.setCantante(Cantante.get());
			
			albumRepository.save(album);
			respuesta.clear();
			respuesta.put("album", album);
			respuesta.put("mensaje", "Álbum añadido correctamente");
			respuesta.put("status", HttpStatus.CREATED);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);

		} catch (Exception e) {
			respuesta.put("mensaje", "Error al registrar el álbum: " + e.getMessage());
			respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> editarAlbum(AlbumDTO dto, Integer id) {

		Map<String, Object> respuesta = new HashMap<>();

		try {
			Optional<AlbumModel> Album = validarAlbumPorId(id, respuesta);
			if (Album.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}
			Optional<CantanteModel> Cantante = validarCantantePorId(dto.getIdCantante(), respuesta);
			if (Cantante.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}

			Optional<AlbumModel> albumDuplicado = validarAlbumDuplicado(dto.getTitulo(), dto.getIdCantante(), respuesta);
			if (albumDuplicado.isPresent() && !albumDuplicado.get().getIdAlbum().equals(id)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
			}

			// Actualizar valores
			AlbumModel album = Album.get();
			album.setTitulo(dto.getTitulo());
			album.setFechaLanzamiento(dto.getFechaLanzamiento());
			album.setImagenUrl(dto.getImagenUrl());
			album.setCantante(Cantante.get());
			albumRepository.saveAndFlush(album);
			respuesta.clear();
			respuesta.put("album", album);
			respuesta.put("mensaje", "Álbum actualizado correctamente");
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);

		} catch (Exception e) {
			respuesta.put("mensaje", "Error al editar el álbum: " + e.getMessage());
			respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> eliminarAlbum(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();

		Optional<AlbumModel> albumExiste = validarAlbumPorId(id, respuesta);

		if (albumExiste.isPresent()) {
			albumRepository.delete(albumExiste.get());
			respuesta.put("mensaje", "Álbum eliminado correctamente");
			respuesta.put("status", HttpStatus.NO_CONTENT);//Eliminado sin contenido en la respuesta
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);//Eliminado sin contenido en la respuesta
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> desactivarAlbum(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();

		Optional<AlbumModel> albumExiste = validarAlbumPorId(id, respuesta);

		if (albumExiste.isPresent()) {
			AlbumModel album = albumExiste.get();
			album.setActivo(false);
			albumRepository.save(album);
			respuesta.put("mensaje", "Álbum desactivado correctamente");
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> activarAlbum(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();

		Optional<AlbumModel> albumExiste = validarAlbumPorId(id, respuesta);
		if (albumExiste.isPresent()) {
			AlbumModel album = albumExiste.get();
			album.setActivo(true);
			albumRepository.save(album);
			respuesta.put("mensaje", "Álbum activado correctamente");
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	private Optional<AlbumModel> validarAlbumPorId(Integer idAlbum,Map<String, Object> respuesta) {

		Optional<AlbumModel> albumOpt = albumRepository.findById(idAlbum);

		if (albumOpt.isEmpty()) {
			respuesta.put("mensaje", "Álbum no encontrado con ID: " + idAlbum);
			respuesta.put("status", HttpStatus.NOT_FOUND);
			respuesta.put("fecha", new Date());
			return Optional.empty();
		}

		return albumOpt;
	}


	private  Optional<CantanteModel>  validarCantantePorId(Integer idCantante,Map<String, Object> respuesta) {
		Optional<CantanteModel> cantanteOpt = cantanteRepository.findById(idCantante);

		if (cantanteOpt.isEmpty()) {
			respuesta.put("mensaje", "Cantante no encontrado con ID: " + idCantante);
			respuesta.put("status", HttpStatus.NOT_FOUND);
			respuesta.put("fecha", new Date());
			return Optional.empty();
		}

		return cantanteOpt;
	}

	private Optional<AlbumModel> validarAlbumDuplicado(String titulo, Integer idCantante,Map<String, Object> respuesta)  {

		Optional<AlbumModel> albumExistente = albumRepository.findByTituloAndCantanteIdCantante(titulo, idCantante);

		if (albumExistente.isPresent()) {
			respuesta.put("albumexistente", albumExistente);
			respuesta.put("mensaje", "Ya existe un álbum con el mismo título para este cantante.");
			respuesta.put("status", HttpStatus.CONFLICT);
			respuesta.put("fecha", new Date());
			return albumExistente;
		}

		return Optional.empty();
	}

}