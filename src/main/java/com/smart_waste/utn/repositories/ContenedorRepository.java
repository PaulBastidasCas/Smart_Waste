package com.smart_waste.utn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_waste.utn.models.Contenedor;

public interface ContenedorRepository extends JpaRepository<Contenedor, Integer>{
    List<Contenedor> findByConActivoTrue();

    List<Contenedor> findByConFacultad_FacId(Integer facultadId);

    List<Contenedor> findByConActivoFalse();
}
