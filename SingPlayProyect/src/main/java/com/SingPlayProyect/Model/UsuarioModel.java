package com.SingPlayProyect.Model;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name = "TB_USUARIO")
@EntityListeners(AuditingEntityListener.class)
public class UsuarioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USUARIO")
	private Integer idUsuario;

	@Column(name = "user", length = 255)
	private String usuario;

	@Column(name = "email", unique = true, length = 255)
	private String email;

	@Column(name = "password", length = 255)
	private String password;
//
//	@Column(name = "fechaRegistro")
//	private Date fecha_registro;

	private boolean activo;
}
