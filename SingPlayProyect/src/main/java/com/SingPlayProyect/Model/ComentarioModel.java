package com.SingPlayProyect.Model;

import java.time.LocalDateTime;

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
@Table(name = "TB_COMENTARIO")
public class ComentarioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_COMENTARIO")
	private Integer idComentario;

	@Column(name = "CONTENIDO", nullable = false, columnDefinition = "TEXT")
	private String contenido;

	@Column(name = "FECHA")
	private LocalDateTime fecha;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioModel usuario;

	@ManyToOne
	@JoinColumn(name = "ID_CANCION")
	private CancionModel cancion;

}
