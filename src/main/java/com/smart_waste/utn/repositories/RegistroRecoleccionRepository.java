package com.smart_waste.utn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_waste.utn.models.RegistroRecoleccion;

public interface RegistroRecoleccionRepository extends JpaRepository<RegistroRecoleccion, Long>{
    
    List<RegistroRecoleccion> findByRegContenedor_ConIdOrderByRegFechaHoraRegistroDesc(Integer conId);
}
