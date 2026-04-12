package org.eduardomango.practicaspringweb.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessage {
    private LocalDateTime timestamp; // Cuando paso
    private int status; // Que tipo de error a nivel HTTP
    private String error; // Categoría del error
    private String message; // El detalle de negocio, el msj personalizado
    private String path; // Donde paso el error
}
