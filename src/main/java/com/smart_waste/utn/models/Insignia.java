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
@Table(name = "insignias")
public class Insignia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @Column(name = "icono_base64", columnDefinition = "TEXT")
    private String iconoBase64;

    @Column(name = "color_hex", nullable = false, length = 7)
    private String colorHex = "#2ECC71";

    @Column(name = "criterio_valor", precision = 10, scale = 2, nullable = false)
    private BigDecimal criterioValor = BigDecimal.ONE;

    @Column(name = "activa", nullable = false)
    private Boolean activa = true;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.creadoEn = LocalDateTime.now();
    }
}
