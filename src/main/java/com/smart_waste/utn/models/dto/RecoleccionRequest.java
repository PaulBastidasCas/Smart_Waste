package com.smart_waste.utn.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RecoleccionRequest {

    @NotNull(message = "El ID del contenedor es obligatorio")
    private Integer contenedorId;

    @NotNull(message = "El peso recolectado es obligatorio")
    @Positive(message = "El peso debe ser mayor a cero")
    private Double pesoRecolectado;

    private String observaciones;

    private String regFotoBase64;

    private Boolean tieneClasificacionErronea;
    
    private String descripcionError;
}
