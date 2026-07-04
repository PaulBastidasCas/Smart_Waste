package com.smart_waste.utn.models.vistas;

import java.math.BigDecimal;
import org.hibernate.annotations.Immutable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
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
