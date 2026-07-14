package com.smart_waste.utn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_waste.utn.models.RetoComunitario;
import com.smart_waste.utn.models.enums.RetoEstado;

public interface RetoComunitarioRepository extends JpaRepository<RetoComunitario, Integer>{

    List<RetoComunitario> findByRetEstado(RetoEstado retEstado);
}
