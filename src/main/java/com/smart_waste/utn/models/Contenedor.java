package com.smart_waste.utn.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.smart_waste.utn.models.enums.EstadoLLeno;
import com.smart_waste.utn.models.enums.Operativo;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contenedores")
public class Contenedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo", nullable = false, unique = true, length = 30)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facultad_id", nullable = false)
    private Facultad facultad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_residuo_id", nullable = false)
    private TipoResiduo tipoResiduo;

    @Column(name = "descripcion_ubicacion", length = 200)
    private String descripcionUbicacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_operativo", nullable = false, length = 30)
    private Operativo estadoOperativo = Operativo.OPERATIVO;

    @Column(name = "nivel_llenado_pct", nullable = false)
    private Integer nivelLlenadoPct = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_llenado", nullable = false, length = 20)
    private EstadoLLeno estadoLlenado = EstadoLLeno.VACIO;

    @Column(name = "capacidad_litros", precision = 8, scale = 2)
    private BigDecimal capacidadLitros;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "latitud", precision = 10, scale = 8)
    private BigDecimal latitud;

    @Column(name = "longitud", precision = 11, scale = 8)
    private BigDecimal longitud;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();

    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.actualizadoEn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.actualizadoEn = LocalDateTime.now();
    }
}
