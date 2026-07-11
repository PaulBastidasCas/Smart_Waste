package com.smart_waste.utn.services;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.RegistroRecoleccion;
import com.smart_waste.utn.repositories.RegistroRecoleccionRepository;

@Service
public class RecoleccionService {

    private final RegistroRecoleccionRepository registroRecoleccionRepository;

    public RecoleccionService(RegistroRecoleccionRepository registroRecoleccionRepository) {
        this.registroRecoleccionRepository = registroRecoleccionRepository;
    }

    @Transactional
    public RegistroRecoleccion registrarVaciado(@NonNull RegistroRecoleccion registro) {
        return registroRecoleccionRepository.save(registro);
    }

    @Transactional(readOnly = true)
    public List<RegistroRecoleccion> listarPorContenedor(Integer contenedorId) {
        return registroRecoleccionRepository.findByRegContenedor_ConIdOrderByRegFechaHoraRegistroDesc(contenedorId);
    }
}
