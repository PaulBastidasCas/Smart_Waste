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
import lombok.Data;

@Data
@Entity
@Table(name = "tokens_revocados")
public class TokenRevocado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "token_jti", nullable = false, unique = true, length = 255)
    private String tokenJwt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "revocado_en", nullable = false)
    private LocalDateTime revocadoEn = LocalDateTime.now();

    @Column(name = "expira_en", nullable = false)
    private LocalDateTime expiraEn;

    @PrePersist
    protected void onCreate(){
        this.revocadoEn = LocalDateTime.now();
    }
}
