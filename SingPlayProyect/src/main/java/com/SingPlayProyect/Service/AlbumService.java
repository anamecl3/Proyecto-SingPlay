package com.SingPlayProyect.Service;
import java.util.Map;
import com.SingPlayProyect.DTO.AlbumDTO;
import org.springframework.http.ResponseEntity;

public interface AlbumService {

	public ResponseEntity<Map<String , Object>> listarAlbumes();
	
	public ResponseEntity<Map<String, Object>> listarAlbumPorId(Integer id);

	public ResponseEntity<Map<String, Object>> agregarAlbum(AlbumDTO Album);

	public ResponseEntity<Map<String, Object>> editarAlbum(AlbumDTO Album, Integer id);

	public ResponseEntity<Map<String, Object>> eliminarAlbum(Integer id);

	public ResponseEntity<Map<String, Object>> desactivarAlbum(Integer id);

	public ResponseEntity<Map<String, Object>> activarAlbum(Integer id);
}
