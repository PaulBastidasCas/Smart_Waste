package com.smart_waste.utn.models;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "historico_niveles_diarios")
public class HistoricoNivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contenedor_id", nullable = false)
    private Contenedor contenedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facultad_id", nullable = false)
    private Facultad facultad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_residuo_id", nullable = false)
    private TipoResiduo tipoResiduo;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "nivel_promedio_pct", precision = 5, scale = 2, nullable = false)
    private BigDecimal nivelPromedio = BigDecimal.ZERO;

    @Column(name = "nivel_maximo_pct", nullable = false)
    private Integer nivelMaximo = 0;

    @Column(name = "nivel_minimo_pct", nullable = false)
    private Integer nivelMinimo = 0;

    @Column(name = "total_kg_dia", precision = 10, scale = 3)
    private BigDecimal totalKgDia = BigDecimal.ZERO;

    @Column(name = "total_recolecciones")
    private Integer totalRecolecciones = 0;

    @Column(name = "errores_clasificacion")
    private Integer erroresClasificacion = 0;

    @Column(name = "registrado_en", nullable = false, updatable = true)
    private LocalDateTime registradoEn = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.registradoEn = LocalDateTime.now();
    }
}
