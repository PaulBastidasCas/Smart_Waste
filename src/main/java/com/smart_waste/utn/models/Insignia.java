package com.smart_waste.utn.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.smart_waste.utn.models.enums.CriterioTipo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "insignias")
public class Insignia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ins_id", nullable = false)
    private Integer insId;

    @Column(name = "ins_nombre", nullable = false, unique = true, length = 100)
    private String insNombre;

    @Column(name = "ins_descripcion", length = 300)
    private String insDescripcion;

    @Column(name = "ins_icono_base64", columnDefinition = "TEXT")
    private String insIconoBase64;

    @Column(name = "ins_color_hex", length = 7)
    private String insColorHex = "#2ECC71";

    @Enumerated(EnumType.STRING)
    @Column(name = "ins_criterio_tipo", nullable = false, length = 50)
    private CriterioTipo insCriterioTipo;

    @Column(name = "ins_criterio_valor", precision = 10, scale = 2, nullable = false)
    private BigDecimal insCriterioValor = BigDecimal.ONE;

    @Column(name = "ins_activa", nullable = false)
    private Boolean insActiva = true;

    @Column(name = "ins_creado_en", nullable = false)
    private LocalDateTime insCreado = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.insCreado = LocalDateTime.now();
    }
}
