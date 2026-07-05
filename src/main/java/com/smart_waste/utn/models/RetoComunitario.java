package com.smart_waste.utn.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.smart_waste.utn.models.enums.EstadoReto;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "retos_comunitarios")
public class RetoComunitario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facultad_id")
    private Facultad facultad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_residuo_id")
    private TipoResiduo tipoResiduo;

    @Column(name = "meta_kg", precision = 10, scale = 3)
    private BigDecimal metaKg;

    @Column(name = "meta_porcentaje", precision = 5, scale = 2)
    private BigDecimal metaPorcentaje;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_meta", nullable = false, length = 30)
    private TipoMeta tipoMeta = TipoMeta.REDUCCION;

    @Column(name = "progreso_actual_kg", precision = 10, scale = 3, nullable = false)
    private BigDecimal progresoActual = BigDecimal.ZERO;

    @Column(name = "progreso_pct", precision = 5, scale = 2, nullable = false)
    private BigDecimal progresoPCT = BigDecimal.ZERO;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoReto estadoReto = EstadoReto.ACTIVO;

    @Column(name = "publico", nullable = false)
    private Boolean publico = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creado_por")
    private Usuario creadoPor;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.creadoEn = LocalDateTime.now();
    }
}
