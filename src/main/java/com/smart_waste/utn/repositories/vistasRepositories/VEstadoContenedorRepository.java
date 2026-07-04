package com.smart_waste.utn.repositories.vistasRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart_waste.utn.models.vistas.VEstadoContenedor;

@Repository
public interface VEstadoContenedorRepository extends JpaRepository<VEstadoContenedor, Long>{

}
