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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "metricas_impacto")
public class MetricaImpacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "met_id", nullable = false)
    private Long metId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "met_facultad_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Facultad metFacultad;

    @Column(name = "met_periodo_inicio", nullable = false)
    private LocalDate metPeriodoInicio;

    @Column(name = "met_periodo_fin", nullable = false)
    private LocalDate metPeriodoFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "met_tipo_residuo_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TipoResiduo metTipoResiduo;

    @Column(name = "met_total_kg_recolectados", precision = 12, scale = 3, nullable = false)
    private BigDecimal metTotalKgRecolectados = BigDecimal.ZERO;

    @Column(name = "met_total_registros", nullable = false)
    private Integer metTotalRegistros = 0;

    @Column(name = "met_tasa_error_clasificacion", precision = 10, scale = 2)
    private BigDecimal metTasaErrorClasificacion = BigDecimal.ZERO;

    @Column(name = "met_valor_economico_usd", precision = 12, scale = 2)
    private BigDecimal metValorEconomicoUsd = BigDecimal.ZERO;

    @Column(name = "met_equivalente_pasajes_bus")
    private Integer metEquivalentePasajesBus = 0;

    @Column(name = "met_equivalente_arboles_co2", precision = 8, scale = 2)
    private BigDecimal metEquivalenteArbolesCo2 = BigDecimal.ZERO;

    @Column(name = "met_calculado_en", nullable = false)
    private LocalDateTime metCalculadoEn = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.metCalculadoEn = LocalDateTime.now();
    }
}
