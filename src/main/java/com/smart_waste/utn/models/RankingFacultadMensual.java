package com.smart_waste.utn.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ranking_facultad_mensual")
public class RankingFacultadMensual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ran_id")
    private Long ranId;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "ran_facultad_id", nullable = false)
    private Facultad ranFacultad;

    @Column(name = "ran_anio", nullable = false)
    private Integer ranAnio;

    @Min(value = 1)
    @Max(value = 12)
    @Column(name = "ran_mes", nullable = false)
    private Integer ranMes;

    @Column(name = "ran_puntos_total", nullable = false)
    private Integer ranPuntosTotal = 0;

    @Column(name = "ran_kg_reciclados", precision = 10, scale = 3, nullable = false)
    private BigDecimal ranKgReciclados = BigDecimal.ZERO;

    @Column(name = "ran_posicion")
    private Integer ranPosicion;

    @Column(name = "ran_actualizado_en", nullable = false)
    private LocalDateTime ranActualizado = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.ranActualizado = LocalDateTime.now();
    }
}
