package com.SingPlayProyect.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "TB_CANCION")
public class CancionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CANCION")
	private Integer idCancion;

	@Column(name = "TITULO", nullable = false, length = 100)
	private String titulo;

	@Column(name = "DURACION")
	private LocalTime duracion;

	@Column(name = "GENERO", length = 50)
	private String genero;

	@Column(name = "URL_AUDIO", length = 255)
	private String urlAudio;

	@ManyToOne
	@JoinColumn(name = "ID_ALBUM")
	private AlbumModel album;

	@ManyToOne
	@JoinColumn(name = "ID_COMPOSITOR")
	private CompositorModel compositor;

	private boolean activo;
}
