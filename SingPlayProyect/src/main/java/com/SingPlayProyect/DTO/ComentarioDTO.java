package com.SingPlayProyect.DTO;

import lombok.Data;

@Data
public class ComentarioDTO {
    private String contenido;
    private Integer idUsuario;
    private Integer idCancion;
}
