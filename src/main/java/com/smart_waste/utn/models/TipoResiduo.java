package com.smart_waste.utn.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.smart_waste.utn.models.enums.CodigoColor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tipos_residuo")
public class TipoResiduo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tre_id", nullable = false)
    private Integer treId;

    @Column(name = "tre_nombre", nullable = false, unique = true, length = 80)
    private String treNombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tre_codigo_color", nullable = false, length = 20)
    private CodigoColor treCodigoColor;

    @Column(name = "tre_color_hex", nullable = false, length = 7)
    private String treColorHex;

    @Column(name = "tre_descripcion", length = 255)
    private String treDescripcion;

    @Column(name = "tre_factor_kg_por_unidad", precision = 8, scale = 4)
    private BigDecimal treFactorKgUnidad = BigDecimal.ONE;

    @Column(name = "tre_precio_mercado_kg", precision = 8, scale = 2)
    private BigDecimal trePrecioMercadoKg = BigDecimal.ZERO;

    @Column(name = "tre_creado_en", nullable = false)
    private LocalDateTime treCreado = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.treCreado = LocalDateTime.now();
    }
}
