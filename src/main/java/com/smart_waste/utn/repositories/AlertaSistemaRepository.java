package com.smart_waste.utn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart_waste.utn.models.AlertaSistema;

@Repository
public interface AlertaSistemaRepository extends JpaRepository<AlertaSistema,Long>{

}
