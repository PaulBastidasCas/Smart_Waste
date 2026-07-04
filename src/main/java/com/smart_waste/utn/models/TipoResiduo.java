package com.smart_waste.utn.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.smart_waste.utn.models.enums.Color;

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
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tipos_residuo")
public class TipoResiduo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 80)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "codigo_color", nullable = false, length = 20)
    private Color codigoColor;

    @Column(name = "color_hex", nullable = false, length = 7)
    private String colorHex;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "factor_kg_por_unidad", precision = 8, scale = 4)
    private BigDecimal factorKgUnidad = new BigDecimal("1.0000");

    @Column(name = "precio_mercado_kg", precision = 8, scale = 2)
    private BigDecimal precioMercadoKg = BigDecimal.ZERO;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.creadoEn = LocalDateTime.now();
    }
}
