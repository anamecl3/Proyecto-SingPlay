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
@Table(name = "TB_COMPOSITOR")
public class CompositorModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_compositor")
	private Integer idCompositor;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "NACIONALIDAD")
	private String nacionalidad;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "IMAGEN_URL")
	private String imagenUrl;

	private boolean activo;
}
