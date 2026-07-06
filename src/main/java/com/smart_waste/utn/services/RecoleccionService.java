package com.smart_waste.utn.services;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.RegistroRecoleccion;
import com.smart_waste.utn.repositories.RegistroRecoleccionRepository;

@Service
public class RecoleccionService {

    private final RegistroRecoleccionRepository recoleccionRepository;

    public RecoleccionService(RegistroRecoleccionRepository recoleccionRepository) {
        this.recoleccionRepository = recoleccionRepository;
    }

    @Transactional(readOnly = true)
    public List<RegistroRecoleccion> obtenerTodasLasRecolecciones() {
        return recoleccionRepository.findAll();
    }

    @Transactional
    public RegistroRecoleccion registrarRecoleccion(@NonNull RegistroRecoleccion registro) {
        return recoleccionRepository.save(registro);
    }
}
