package com.SingPlayProyect.DTO;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CancionDTO {
    private String tituloCancion;
    private LocalTime duracionCancion;
    private String generoCancion;
    private String urlCancion;
    private Integer idAlbum;
    private Integer idCompositor;
}
