package com.SingPlayProyect.DTO;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AlbumDTO {
    private String titulo;
    private LocalDate fechaLanzamiento;
    private String imagenUrl;
    private Integer idCantante;
}
