package com.smart_waste.utn.models;

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
@Table(name = "tipos_identificacion")
public class TipoIdentificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tid_id", nullable = false)
    private Integer tidId;

    @Column(name = "tid_nombre", nullable = false, unique = true, length = 50)
    private String tidNombre;

    @Column(name = "tid_descripcion", length = 200)
    private String tidDescripcion;

    @Column(name = "tid_creado_en")
    private LocalDateTime tidCreado = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.tidCreado = LocalDateTime.now();
    }
}
