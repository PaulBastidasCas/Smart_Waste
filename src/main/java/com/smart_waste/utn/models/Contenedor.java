package com.smart_waste.utn.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.smart_waste.utn.models.enums.EstadoLlenado;
import com.smart_waste.utn.models.enums.EstadoOperativo;

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
import jakarta.persistence.PreUpdate;
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
@Table(name = "contenedores")
public class Contenedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "con_id", nullable = false)
    private Integer conId;

    @Column(name = "con_codigo", nullable = false, unique = true, length = 30)
    private String conCodigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "con_facultad_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Facultad conFacultad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "con_tipo_residuo_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TipoResiduo conTipoResiduo;

    @Column(name = "con_descripcion_ubicacion", length = 200)
    private String conDescripcionUbicacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "con_estado_operativo", nullable = false)
    private EstadoOperativo conEstadoOperativo = EstadoOperativo.OPERATIVO;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "con_nivel_llenado_pct", nullable = false)
    private Integer conNivelLlenadoPct = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "con_estado_llenado", nullable = false, length = 20)
    private EstadoLlenado conEstadoLlenado;

    @Column(name = "con_capacidad_litros", precision = 8, scale = 2)
    private BigDecimal conCapacidadLitros;

    @Column(name = "con_activo", nullable = false)
    private Boolean conActivo = true;

    @Column(name = "con_latitud", precision = 10, scale = 8)
    private BigDecimal conLatitud;

    @Column(name = "con_longitud", precision = 11, scale = 8)
    private BigDecimal conLongitud;

    @Column(name = "con_creado_en", nullable = false)
    private LocalDateTime conCreado = LocalDateTime.now();

    @Column(name = "con_actualizado_en", nullable = false)
    private LocalDateTime conActualizado = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.conCreado = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.conActualizado = LocalDateTime.now();
    }
}