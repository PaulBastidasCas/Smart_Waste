package com.smart_waste.utn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_waste.utn.models.RegistroRecoleccion;


public interface RegistroRecoleccionRepository extends JpaRepository<RegistroRecoleccion, Long>{
    
    List<RegistroRecoleccion> findByRegContenedor_ConIdOrderByRegFechaHoraRegistroDesc(Integer conId);
    List<RegistroRecoleccion> findByRegContenedor_ConFacultad_FacId(Integer facId);
    List<RegistroRecoleccion> findTop3ByOrderByRegFechaHoraRegistroDesc();
    List<RegistroRecoleccion> findByRegTieneClasificacionErroneaTrue();
}
