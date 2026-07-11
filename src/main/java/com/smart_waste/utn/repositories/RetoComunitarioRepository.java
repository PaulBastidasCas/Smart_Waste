package com.smart_waste.utn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_waste.utn.models.RetoComunitario;

public interface RetoComunitarioRepository extends JpaRepository<RetoComunitario, Integer>{

    List<RetoComunitario> findByRetEstado(String retEstado);
}
