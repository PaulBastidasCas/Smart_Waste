package com.smart_waste.utn.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.smart_waste.utn.models.enums.RetoEstado;
import com.smart_waste.utn.models.enums.TipoMeta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "retos_comunitarios")
public class RetoComunitario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ret_id", nullable = false)
    private Integer retId;

    @Column(name = "ret_titulo", nullable = false, length = 200)
    private String retTitulo;

    @Column(name = "ret_descripcion", length = 500)
    private String retDescripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "ret_facultad_id")
    private Facultad retFacultad;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "ret_tipo_residuo_id")
    private TipoResiduo retTipoResiduo;

    @Column(name = "ret_meta_kg", precision = 10, scale = 3)
    private BigDecimal retMetaKg;

    @Column(name = "ret_meta_porcentaje", precision = 5, scale = 2)
    private BigDecimal retMetaPorcentaje;

    @Enumerated(EnumType.STRING)
    @Column(name = "ret_tipo_meta", length = 30, nullable = false)
    private TipoMeta retTipoMeta = TipoMeta.REDUCCION;

    @Column(name = "ret_progreso_actual_kg", precision = 10, scale = 3, nullable = false)
    private BigDecimal retProgresoActualKg = BigDecimal.ZERO;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "ret_progreso_pct", precision = 5, scale = 2, nullable = false)
    private BigDecimal retProgresoPct = BigDecimal.ZERO;

    @Column(name = "ret_fecha_inicio", nullable = false)
    private LocalDate retFechaInicio;

    @Column(name = "ret_fecha_fin", nullable = false)
    private LocalDate retFechaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "ret_estado", nullable = false)
    private RetoEstado retEstado = RetoEstado.ACTIVO;

    @Column(name = "ret_publico", nullable = false)
    private Boolean retPublico = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "ret_creado_por")
    private Usuario retCreado;

    @Column(name = "ret_creado_en", nullable = false)
    private LocalDateTime retCreadoEn = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.retCreadoEn = LocalDateTime.now();
    }
}
