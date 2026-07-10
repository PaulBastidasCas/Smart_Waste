package com.smart_waste.utn.models;

import java.time.LocalDateTime;

import com.smart_waste.utn.models.enums.NivelUrgencia;
import com.smart_waste.utn.models.enums.TipoAlerta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
@Table(name = "alertas_sistema")
public class AlertaSistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ale_id", nullable = false)
    private Long aleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ale_tipo_alerta", nullable = false, length = 50)
    private TipoAlerta aleTipoAlerta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ale_contenedor_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Contenedor aleContenedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ale_facultad_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Facultad aleFacultad;

    @Column(name = "ale_mensaje", nullable = false, length = 500)
    private String aleMensaje;

    @Enumerated(EnumType.STRING)
    @Column(name = "ale_nivel_urgencia", nullable = false, length = 20)
    private NivelUrgencia aleNivelUrgencia = NivelUrgencia.MEDIA;

    @Column(name = "ale_leida", nullable = false)
    private Boolean aleLeida = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ale_leida_por")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Usuario aleLeidaPor;

    @Column(name = "ale_leida_en")
    private LocalDateTime aleLeidaEn;

    @Column(name = "ale_generada_en", nullable = false)
    private LocalDateTime aleGeneradaEn = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.aleGeneradaEn = LocalDateTime.now();
    }
}
