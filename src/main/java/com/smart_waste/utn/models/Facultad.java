package com.smart_waste.utn.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "facultades")
public class Facultad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fac_id", nullable = false)
    private Integer facId;

    @Column(name = "fac_codigo", nullable = false, unique = true, length = 20)
    private String facCodigo;

    @Column(name = "fac_nombre", nullable = false, length = 150)
    private String facNombre;

    @Column(name = "fac_descripcion" , length = 300)
    private String facDescripcion;

    @Column(name = "fac_latitud", precision = 10, scale = 8)
    private BigDecimal facLatitud;

    @Column(name = "fac_longitud", precision = 11, scale = 8)
    private BigDecimal facLongitud;

    @Column(name = "fac_activa", nullable = false)
    private Boolean facActiva = true;

    @Column(name = "fac_creado_en")
    private LocalDateTime facCreado = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.facCreado = LocalDateTime.now();
    }
}
