package com.smart_waste.utn.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.smart_waste.utn.models.enums.EstadoLLeno;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registros_recoleccion")
public class RegistroRecoleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contenedor_id", nullable = false)
    private Contenedor contenedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encargado_id", nullable = false)
    private Usuario encargado;

    @Column(name = "nivel_antes_pct", nullable = false)
    private Integer nivelAntesPct;

    @Column(name = "nivel_despues_pct", nullable = false)
    private Integer nivelDespuesPct = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_al_registrar", nullable = false, length = 20)
    private EstadoLLeno estadoAlRegistrar;

    @Column(name = "peso_estimado_kg", precision = 8, scale = 3)
    private BigDecimal pesoEstimadoKg;

    @Column(name = "volumen_estimado_l", precision = 8, scale = 3)
    private BigDecimal volumenEstimado;

    @Column(name = "tiene_clasificacion_erronea", nullable = false)
    private Boolean tieneClasificacionErronea = false;

    @Column(name = "descripcion_error", length = 300)
    private String descripcionError;

    @Column(name = "foto_base64", columnDefinition = "TEXT")
    private String fotoBase64;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Column(name = "fecha_hora_registro", nullable = false)
    private LocalDateTime fechaHoraRegistro = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_registro", nullable = false, length = 20)
    private EstadoRegistro estadoRegistro = EstadoRegistro.COMPLETADO;

    @PrePersist
    protected void onCreate(){
        this.fechaHoraRegistro = LocalDateTime.now();
    }
}
