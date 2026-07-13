package com.smart_waste.utn.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.smart_waste.utn.models.enums.EstadoLlenado;
import com.smart_waste.utn.models.enums.EstadoRegistro;

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
@Table(name = "registros_recoleccion")
public class RegistroRecoleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reg_id", nullable = false)
    private Long regId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_contenedor_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Contenedor regContenedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_encargado_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Usuario regEncargado;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "reg_nivel_antes_pct", nullable = false)
    private Integer regNivelAntesPct;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "reg_nivel_despues_pct", nullable = false)
    private Integer regNivelDespuesPct = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "reg_estado_al_registrar", nullable = false, length = 20)
    private EstadoLlenado regEstadoLLenado;

    @Column(name = "reg_peso_estimado_kg", precision = 8, scale = 3)
    private BigDecimal regPesoEstimadoKg;

    @Column(name = "reg_volumen_estimado_l", precision = 8, scale = 3)
    private BigDecimal regVolumenEstimado;

    @Column(name = "reg_tiene_clasificacion_erronea", nullable = false)
    private Boolean regTieneClasificacionErronea = false;

    @Column(name = "reg_descripcion_error", length = 300)
    private String regDescripcionError;

    @Column(name = "reg_foto_base64", columnDefinition = "TEXT")
    private String regFotoBase64;

    @Column(name = "reg_observaciones", length = 500)
    private String regObservaciones;

    @Column(name = "reg_fecha_hora_registro", nullable = false)
    private LocalDateTime regFechaHoraRegistro = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "reg_estado_registro", nullable = false, length = 20)
    private EstadoRegistro regEstadoRegistro = EstadoRegistro.COMPLETADO;

    @PrePersist
    protected void onCreate(){
        this.regFechaHoraRegistro = LocalDateTime.now();
    }
}
