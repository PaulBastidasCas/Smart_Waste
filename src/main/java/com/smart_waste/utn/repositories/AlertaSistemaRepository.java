package com.smart_waste.utn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_waste.utn.models.AlertaSistema;

public interface AlertaSistemaRepository extends JpaRepository<AlertaSistema, Long>{

    List<AlertaSistema> findByAleLeidaFalse();
}
