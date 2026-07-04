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
@Table(name = "metricas_impacto")
public class MetricaImpacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facultad_id")
    private Facultad facultad;

    @Column(name = "periodo_inicio", nullable = false)
    private LocalDate periodoInicio;

    @Column(name = "periodo_fin", nullable = false)
    private LocalDate periodoFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_residuo_id")
    private TipoResiduo tipoResiduo;

    @Column(name = "total_kg_recolectados", precision = 12, scale = 3, nullable = false)
    private BigDecimal totalKgRecolectados = BigDecimal.ZERO;

    @Column(name = "total_registros", nullable = false)
    private Integer totalRegistros = 0;

    @Column(name = "tasa_error_clasificacion", precision = 5, scale = 2)
    private BigDecimal tasaErrorClasificacion = BigDecimal.ZERO;

    @Column(name = "valor_economico_usd", precision = 12, scale = 2)
    private BigDecimal valorEconomicoUsd = BigDecimal.ZERO;

    @Column(name = "equivalente_pasajes_bus")
    private Integer equivalentePasajesBus = 0;

    @Column(name = "equivalente_arboles_co2", precision = 8, scale = 2)
    private BigDecimal equivalenteArboles = BigDecimal.ZERO;

    @Column(name = "calculado_en", nullable = false)
    private LocalDateTime calculadoEn = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.calculadoEn = LocalDateTime.now();
    }
}
