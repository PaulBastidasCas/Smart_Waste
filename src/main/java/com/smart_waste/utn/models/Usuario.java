package com.smart_waste.utn.models;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "usu_id", nullable = false)
    private UUID usuId;

    @Column(name = "usu_identificacion", unique = true, length = 20)
    private String usuIdentificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usu_tipo_identificacion_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TipoIdentificacion usuTipoIdentificacion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usu_rol_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Rol usuRol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usu_facultad_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Facultad usuFacultad;

    @Column(name = "usu_nombre", nullable = false, length = 100)
    private String usuNombre;

    @Column(name = "usu_apellido", nullable = false, length = 100)
    private String usuApellido;

    @Column(name = "usu_correo", nullable = false, length = 150, unique = true)
    private String usuCorreo;

    @Column(name = "usu_password_hash", nullable = false, length = 255)
    private String usuPasswordHash;

    @Column(name = "usu_fotoperfil_base64", columnDefinition = "TEXT")
    private String usuFotoPerfilBase64;

    @Column(name = "usu_activo", nullable = false)
    private Boolean usuActivo = true;

    @Column(name = "usu_ultimo_login")
    private LocalDateTime usuUltimoLogin;

    @Column(name = "usu_creado_en", nullable = false)
    private LocalDateTime usuCreado = LocalDateTime.now();

    @Column(name = "usu_actualizado_en", nullable = false)
    private LocalDateTime usuActualizado = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.usuCreado = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.usuActualizado = LocalDateTime.now();
    }
}