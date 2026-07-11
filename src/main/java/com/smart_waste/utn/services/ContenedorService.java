package com.smart_waste.utn.services;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.Contenedor;
import com.smart_waste.utn.repositories.ContenedorRepository;

@Service
public class ContenedorService {

    private final ContenedorRepository contenedorRepository;

    public ContenedorService(ContenedorRepository contenedorRepository){
        this.contenedorRepository = contenedorRepository;
    }

    @Transactional(readOnly = true)
    public List<Contenedor> listarActivos() {
        return contenedorRepository.findByConActivoTrue(); 
    }

    @Transactional
    public void simularLecturaNivel(@NonNull Integer contenedorId, Integer nuevoNivelPct) {
        Contenedor contenedor = contenedorRepository.findById(contenedorId)
                .orElseThrow(() -> new RuntimeException("Contenedor no encontrado"));
        
        contenedor.setConNivelLlenadoPct(nuevoNivelPct);
        contenedorRepository.save(contenedor);
    }
}
