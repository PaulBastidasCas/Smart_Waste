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
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id", nullable = false)
    private Integer rolId;

    @Column(name = "rol_nombre", nullable = false, unique = true, length = 50)
    private String rolNombre;

    @Column(name = "rol_descripcion", length = 300)
    private String rolDescripcion;

    @Column(name = "rol_creado_en")
    private LocalDateTime rolCreado = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.rolCreado = LocalDateTime.now();
    }
}
