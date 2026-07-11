package com.smart_waste.utn.models.views;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Immutable
@IdClass(VFeedInsigniasUsuarioId.class)
@Table(name = "v_feed_insignias_usuario")
public class VFeedInsigniasUsuario {

    @Id
    @Column(name = "usu_id")
    private UUID usuId;

    @Column(name = "usu_identificacion")
    private String usuIdentificacion;

    @Column(name = "usu_facultad_id")
    private Integer usuFacultadId;

    @Column(name = "facultad_codigo")
    private String facultadCodigo;

    @Id
    @Column(name = "ins_id")
    private Integer insId;

    @Column(name = "ins_nombre")
    private String insNombre;

    @Column(name = "ins_descripcion")
    private String insDescripcion;

    @Column(name = "ins_icono_url")
    private String insIconoUrl;

    @Column(name = "ins_color_hex")
    private String insColorHex;

    @Column(name = "fin_ganada_en")
    private LocalDateTime finGanadaEn;
}
