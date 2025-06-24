package com.SingPlayProyect.ServiceImplemnt;

import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SingPlayProyect.DTO.CancionDTO;
import com.SingPlayProyect.DTO.CompositorDTO;
import com.SingPlayProyect.Model.AlbumModel;
import com.SingPlayProyect.Model.CantanteModel;
import com.SingPlayProyect.Model.CompositorModel;
import com.SingPlayProyect.Repository.AlbumRepository;
import com.SingPlayProyect.Repository.CompositorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.SingPlayProyect.Model.CancionModel;
import com.SingPlayProyect.Repository.CancionRepository;
import com.SingPlayProyect.Service.CancionService;

@Service
public class CancionServiceImplement implements CancionService{

	
	@Autowired 
	private CancionRepository cancionRepository;
	@Autowired
	private AlbumRepository albumRepository;
	@Autowired
	private CompositorRepository compositorRepository;

	@Override
	public ResponseEntity<Map<String, Object>> listarCanciones() {
		Map<String, Object> respuesta = new HashMap<>();
		List<CancionModel> cancion = cancionRepository.findAll();

		if (!cancion.isEmpty()) {
			respuesta.put("mensaje", "Lista de canciones");
			respuesta.put("fecha", new Date());
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("cancion", cancion);
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} else {
			respuesta.put("mensaje", "No existen registros");
			respuesta.put("fecha", new Date());
			respuesta.put("status", HttpStatus.NOT_FOUND);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> listarCancionPorId(Integer idCancion) {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<CancionModel> cancionExiste = validarCancionPorId(idCancion,respuesta);

		if (cancionExiste.isPresent()) {
			respuesta.put("cancion", cancionExiste.get());
			respuesta.put("mensaje", "Búsqueda correcta");
			respuesta.put("fecha", new Date());
			respuesta.put("status", HttpStatus.OK);
			return ResponseEntity.ok().body(respuesta);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> agregarCancion(CancionDTO dto) {
		Map<String, Object> respuesta = new HashMap<>();

		try {

			Optional<AlbumModel> Album = validarAlbumPorId(dto.getIdAlbum(), respuesta);
			if (Album.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}

			Optional<CompositorModel> Compositor = validarCompositorPorId(dto.getIdCompositor(), respuesta);
			if (Compositor.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}

			Optional<CancionModel> cancionDuplicado = validarCancionDuplicado(dto.getTituloCancion(), dto.getIdCompositor(),dto.getIdAlbum(), respuesta);
			if (cancionDuplicado.isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
			}

			CancionModel cancion = new CancionModel();
			cancion.setTitulo(dto.getTituloCancion());
			cancion.setDuracion(dto.getDuracionCancion());
			cancion.setGenero(dto.getGeneroCancion());
			cancion.setUrlAudio(dto.getUrlCancion());
			cancion.setAlbum(Album.get());
			cancion.setCompositor(Compositor.get());
			cancion.setActivo(true);
			cancionRepository.save(cancion);

			respuesta.clear();
			respuesta.put("cancion", cancion);
			respuesta.put("mensaje", "Canción añadida correctamente");
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
	public ResponseEntity<Map<String, Object>> editarCancion(CancionDTO dto, Integer id) {
		Map<String, Object> respuesta = new HashMap<>();
		try {


			Optional<CancionModel> Cancion = validarCancionPorId(id, respuesta);
			if (Cancion.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}
			Optional<AlbumModel> Album = validarAlbumPorId(dto.getIdAlbum(), respuesta);
			if (Album.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}
			Optional<CompositorModel> Compositor = validarCompositorPorId(dto.getIdCompositor(), respuesta);
			if (Compositor.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}
			Optional<CancionModel> cancionDuplicado = validarCancionDuplicado(dto.getTituloCancion(), dto.getIdCompositor(),dto.getIdAlbum(), respuesta);
			if (cancionDuplicado.isPresent() && !cancionDuplicado.get().getIdCancion().equals(id)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
			}
			CancionModel cancion =  Cancion.get();
			cancion.setTitulo(dto.getTituloCancion());
			cancion.setDuracion(dto.getDuracionCancion());
			cancion.setGenero(dto.getGeneroCancion());
			cancion.setUrlAudio(dto.getUrlCancion());
			cancion.setAlbum(Album.get());
			cancion.setCompositor(Compositor.get());
			cancionRepository.saveAndFlush(cancion);

			respuesta.clear();
			respuesta.put("cancion", cancion);
			respuesta.put("mensaje", "Canción actualizada correctamente");
			respuesta.put("status", HttpStatus.CREATED);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);

		} catch (Exception e) {
			respuesta.put("mensaje", "Error al registrar el canción: " + e.getMessage());
			respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> eliminarCancion(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<CancionModel> cancionExistente = validarCancionPorId(id, respuesta);

		if (cancionExistente.isPresent()) {
			cancionRepository.delete(cancionExistente.get());
			respuesta.put("mensaje", "cancion eliminada correctamente");
			respuesta.put("fecha", new Date());
			respuesta.put("status", HttpStatus.OK);
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> desactivarCancion(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();

		Optional<CancionModel> cancionExistente = validarCancionPorId(id, respuesta);

		if (cancionExistente.isPresent()) {
			CancionModel cancion =cancionExistente.get();
			cancion.setActivo(false);
			cancionRepository.save(cancion);
			respuesta.put("mensaje", "Canción desactivada correctamente");
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> activarCancion(Integer id) {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<CancionModel> cancionExistente = validarCancionPorId(id, respuesta);

		if (cancionExistente.isPresent()) {
			CancionModel cancion =cancionExistente.get();
			cancion.setActivo(true);
			cancionRepository.save(cancion);
			respuesta.put("mensaje", "Canción activada correctamente");
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
	private Optional<AlbumModel> validarAlbumPorId(Integer idAlbum, Map<String, Object> respuesta) {

		Optional<AlbumModel> albumOpt = albumRepository.findById(idAlbum);

		if (albumOpt.isEmpty()) {
			respuesta.put("mensaje", "Álbum no encontrado con ID: " + idAlbum);
			respuesta.put("status", HttpStatus.NOT_FOUND);
			respuesta.put("fecha", new Date());
			return Optional.empty();
		}

		return albumOpt;
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

	private Optional<CancionModel> validarCancionDuplicado(String titulo, Integer idCompositor,Integer idAlbum, Map<String, Object> respuesta)  {

		Optional<CancionModel> cancionExistente = cancionRepository.findByTituloAndCompositor_IdCompositorAndAlbum_IdAlbum(titulo,idCompositor,idAlbum);

		if (cancionExistente.isPresent()) {
			respuesta.put("cancionExistente", cancionExistente);
			respuesta.put("mensaje", "Ya existe una cancion con el mismo título,compositor y album .");
			respuesta.put("status", HttpStatus.CONFLICT);
			respuesta.put("fecha", new Date());
			return cancionExistente;
		}

		return Optional.empty();
	}
}
