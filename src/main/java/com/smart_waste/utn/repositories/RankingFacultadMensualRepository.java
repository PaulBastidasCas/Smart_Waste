package com.smart_waste.utn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_waste.utn.models.RankingFacultadMensual;

public interface RankingFacultadMensualRepository extends JpaRepository<RankingFacultadMensual, Long>{

    List<RankingFacultadMensual> findByRanAnioAndRanMesOrderByRanPuntosTotalDesc(Integer ranAnio, Integer ranMes);
}
