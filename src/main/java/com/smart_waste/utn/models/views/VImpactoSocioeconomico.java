package com.smart_waste.utn.models.views;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Immutable
@Table(name = "v_impacto_socioeconomico")
public class VImpactoSocioeconomico {

    @Id
    @Column(name = "tipo_residuo")
    private String tipoResiduo;

    @Column(name = "color_tacho")
    private String colorTacho;

    @Column(name = "kg_mes_actual")
    private BigDecimal kgMesActual;

    @Column(name = "valor_usd_mes")
    private BigDecimal valorUsdMes;

    @Column(name = "equivalente_pasajes_bus")
    private BigDecimal equivalentePasajesBus;
}