package com.smart_waste.utn.models.views;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Immutable
@Table(name = "v_estado_contenedores")
public class VEstadoContenedores {

    @Id
    @Column(name = "contenedor_id")
    private Integer contenedorId;

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

    @Column(name = "latitud", precision = 10, scale = 8)
    private BigDecimal latitud;

    @Column(name = "longitud", precision = 10, scale = 8)
    private BigDecimal longitud;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;
}
