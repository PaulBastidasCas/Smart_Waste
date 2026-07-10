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
@Table(name = "historico_niveles_diarios")
public class HistoricoNivelDiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "his_id", nullable = false)
    private Long hisId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "his_contenedor_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Contenedor hisContenedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "his_facultad_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Facultad hisFacultad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "his_tipo_residuo_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TipoResiduo hisTipoResiduo;

    @Column(name = "his_fecha", nullable = false)
    private LocalDate hisFecha;

    @Column(name = "his_nivel_promedio_pct", precision = 5, scale = 2, nullable = false)
    private BigDecimal hisNivelPromedioPct = BigDecimal.ZERO;

    @Column(name = "his_nivel_maximo_pct", nullable = false)
    private Integer hisNivelMaximoPct = 0;

    @Column(name = "his_nivel_minimo_pct", nullable = false)
    private Integer hisNivelMinimoPct = 0;

    @Column(name = "his_total_kg_dia", precision = 10, scale = 3)
    private BigDecimal hisTotalKgDia = BigDecimal.ZERO;

    @Column(name = "his_total_recolecciones")
    private Integer hisTotalRecolecciones = 0;

    @Column(name = "his_errores_clasificacion")
    private Integer hisErroresClasificacion = 0;

    @Column(name = "his_registrado_en", nullable = false)
    private LocalDateTime hisRegistradoEn = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.hisRegistradoEn = LocalDateTime.now();
    }
}
