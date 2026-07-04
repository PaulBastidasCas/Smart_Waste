package com.smart_waste.utn.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "ranking_mensual",
    uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "anio", "mes"})
)
public class RankingMensual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "anio", nullable = false)
    private Integer anio;

    @Min(value = 1, message = "El mes no puede ser menor a 1")
    @Max(value = 12, message = "El mes no puede ser mayor a 12")
    @Column(name = "mes", nullable = false)
    private Integer mes;

    @Column(name = "puntos_total", nullable = false)
    private Integer puntosTotal = 0;

    @Column(name = "kg_reciclados", precision = 10, scale = 3, nullable = false)
    private BigDecimal kgReciclados = BigDecimal.ZERO;

    @Column(name = "posicion")
    private Integer posicion;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate(){
        this.actualizadoEn = LocalDateTime.now();
    }
}
