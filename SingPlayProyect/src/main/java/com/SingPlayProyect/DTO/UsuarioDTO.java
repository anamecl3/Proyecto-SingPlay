package com.SingPlayProyect.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioDTO {
    private String usuario;
    private String correo;
    private String clave;
}
