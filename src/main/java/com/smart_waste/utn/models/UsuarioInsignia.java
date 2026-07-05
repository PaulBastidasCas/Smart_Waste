package com.smart_waste.utn.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "usuario_insignias",
    uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "insignia_id"})
)
public class UsuarioInsignia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insignia_id", nullable = false)
    private Insignia insignia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reto_id")
    private RetoComunitario retoComunitario;

    @Column(name = "ganada_en", nullable = false)
    private LocalDateTime ganadaEn = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        this.ganadaEn = LocalDateTime.now();
    }
}
