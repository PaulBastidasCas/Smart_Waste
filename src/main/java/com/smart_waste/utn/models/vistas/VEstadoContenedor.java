package com.smart_waste.utn.models.vistas;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Immutable
@Table(name = "v_estado_contenedores")
public class VEstadoContenedor {

    @Id
    @Column(name = "contenedor_id")
    private Integer contenedorID;

    @Column(name = "contenedor_codigo")
    private String contenedorCodigo;

    @Column(name = "facultad_codigo")
    private String facultadCodigo;

    @Column(name = "facultad_nombre")
    private String facultadNombre;

    @Column(name = "tipo_residuo")
    private String tipoResiduo;

    @Column(name = "color_tacho")
    private String colorTacho;

    @Column(name = "color_ui")
    private String colorUi;

    @Column(name = "nivel_llenado_pct")
    private Integer nivelLlenadoPct;

    @Column(name = "estado_llenado")
    private String estadoLlenado;

    @Column(name = "estado_operativo")
    private String estadoOperativo;

    @Column(name = "descripcion_ubicacion")
    private String descripcionUbicacion;

    @Column(name = "latitud")
    private BigDecimal latitud;

    @Column(name = "longitud")
    private BigDecimal longitud;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;
}
