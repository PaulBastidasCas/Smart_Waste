package com.smart_waste.utn.models.dto;

import java.math.BigDecimal;

import com.smart_waste.utn.models.enums.CriterioTipo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class InsigniaRequest {

    @NotBlank(message = "El nombre de la insignia es obligatorio")
    private String insNombre;

    private String insDescripcion;

    private String insIconoBase64;

    private String insColorHex;

    @NotNull(message = "El tipo de criterio es obligatorio")
    private CriterioTipo insCriterioTipo;

    @NotNull(message = "El valor del criterio es obligatorio")
    @Positive(message = "El valor del criterio debe ser positivo")
    private BigDecimal insCriterioValor;
}