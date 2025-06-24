package com.SingPlayProyect.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_ALBUM")
public class AlbumModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ALBUM")
	private Integer idAlbum;

	@Column(name = "TITULO", nullable = false, length = 100)
	private String titulo;

	@Column(name = "FECHA_LANZAMIENTO")
	private LocalDate fechaLanzamiento;

	@Column(name = "IMAGEN_URL", length = 255)
	private String imagenUrl;

	@ManyToOne
	@JoinColumn(name = "ID_CANTANTE")
	private CantanteModel cantante;

	private boolean activo;
}
