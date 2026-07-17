package com.smart_waste.utn.services;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.Contenedor;
import com.smart_waste.utn.models.enums.EstadoLlenado;
import com.smart_waste.utn.models.enums.EstadoOperativo;
import com.smart_waste.utn.repositories.ContenedorRepository;
import com.smart_waste.utn.exceptions.ResourceNotFoundException;

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

    public List<Contenedor> listarPorFacultad(Integer facultadId) {
        return contenedorRepository.findByConFacultad_FacId(facultadId);
    }
    
    @Transactional
    public void simularLecturaNivel(@NonNull Integer contenedorId, Integer nuevoNivelPct) {
        Contenedor contenedor = contenedorRepository.findById(contenedorId)
                .orElseThrow(() -> new ResourceNotFoundException("Contenedor no encontrado"));
        
        contenedor.setConNivelLlenadoPct(nuevoNivelPct);
        contenedorRepository.save(contenedor);
    }

    @Transactional
    public void actualizarPesoSensor(Integer contenedorId, Double pesoAgregado, Boolean reportarError, String descripcionError) {
        Contenedor contenedor = contenedorRepository.findById(contenedorId)
            .orElseThrow(() -> new ResourceNotFoundException("Contenedor no encontrado"));

        double capacidad = contenedor.getConCapacidadLitros().doubleValue();
        double volumenActual = (capacidad * contenedor.getConNivelLlenadoPct()) / 100.0;
        
        double nuevoVolumen = Math.min(capacidad, volumenActual + pesoAgregado);
        int nuevoPorcentaje = (int) Math.round((nuevoVolumen / capacidad) * 100.0);

        contenedor.setConNivelLlenadoPct(nuevoPorcentaje);

        if (nuevoPorcentaje >= 90) { 
            contenedor.setConEstadoLlenado(EstadoLlenado.CRITICO);
        } else if (nuevoPorcentaje >= 50) {
            contenedor.setConEstadoLlenado(EstadoLlenado.MEDIO);
        } else {
            contenedor.setConEstadoLlenado(EstadoLlenado.VACIO);
        }

        if (Boolean.TRUE.equals(reportarError)) {
            contenedor.setConEstadoOperativo(EstadoOperativo.REQUIERE_MANTENIMIENTO);
            contenedor.setConDescripcionUbicacion(
                contenedor.getConDescripcionUbicacion() + " [AVERÍA REPORTADA: " + descripcionError + "]"
            );
        }

        contenedorRepository.save(contenedor);
    }
}
