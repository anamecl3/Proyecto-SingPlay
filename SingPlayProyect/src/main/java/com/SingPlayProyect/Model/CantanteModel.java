package com.SingPlayProyect.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_CANTANTE")
public class CantanteModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CANTANTE")
	private Integer idCantante;

	@Column(name = "NOMBRE", nullable = false, length = 100)
	private String nombre;

	@Column(name = "GENERO", length = 50)
	private String genero;

	@Column(name = "DESCRIPCION", columnDefinition = "TEXT")
	private String descripcion;

	@Column(name = "IMAGEN_URL", length = 255)
	private String imagenUrl;

	private boolean activo;
}
