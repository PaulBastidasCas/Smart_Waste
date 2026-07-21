package com.smart_waste.utn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_waste.utn.models.RetoComunitario;
import com.smart_waste.utn.models.enums.RetoEstado;
import com.smart_waste.utn.models.enums.TipoMeta;

public interface RetoComunitarioRepository extends JpaRepository<RetoComunitario, Integer>{

    List<RetoComunitario> findByRetEstado(RetoEstado retEstado);
    List<RetoComunitario> findByRetFacultad_FacIdAndRetEstado(Integer facId, RetoEstado retEstado);
    List<RetoComunitario> findByRetFacultad_FacIdAndRetEstadoAndRetTipoMeta(Integer facId, RetoEstado retEstado, TipoMeta retTipoMeta);
}
