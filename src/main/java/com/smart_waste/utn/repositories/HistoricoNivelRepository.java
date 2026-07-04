package com.smart_waste.utn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart_waste.utn.models.HistoricoNivel;

@Repository
public interface HistoricoNivelRepository extends JpaRepository<HistoricoNivel, Long>{

}
