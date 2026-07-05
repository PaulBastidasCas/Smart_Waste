package com.smart_waste.utn.models.vistas;

import java.math.BigDecimal;
import org.hibernate.annotations.Immutable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Immutable
@Table(name = "v_kpi_facultad")
public class VKpiFacultad {

    @Id
    @Column(name = "facultad_id")
    private Integer facultadId;

    @Column(name = "facultad_codigo")
    private String facultadCodigo;

    @Column(name = "facultad_nombre")
    private String facultadNombre;

    @Column(name = "total_contenedores")
    private Long totalContenedores;

    @Column(name = "total_recolecciones")
    private Long totalRecolecciones;

    @Column(name = "total_kg_recolectados")
    private BigDecimal totalKgRecolectados;

    @Column(name = "nivel_promedio_pct")
    private BigDecimal nivelPromedioPct;

    @Column(name = "errores_clasificacion")
    private Long erroresClasificacion;
}
