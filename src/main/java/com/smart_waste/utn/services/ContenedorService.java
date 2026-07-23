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

    @Transactional(readOnly = true)
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
    public void actualizarPesoSensor(@NonNull Integer contenedorId, Double pesoAgregado, Boolean reportarError, String descripcionError) {
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

    @Transactional
    public Contenedor guardarContenedor(Contenedor contenedor) {
        if (contenedor.getConNivelLlenadoPct() == null) {
            contenedor.setConNivelLlenadoPct(0);
        }
        if (contenedor.getConEstadoLlenado() == null) {
            contenedor.setConEstadoLlenado(com.smart_waste.utn.models.enums.EstadoLlenado.VACIO);
        }
        if (contenedor.getConEstadoOperativo() == null) {
            contenedor.setConEstadoOperativo(com.smart_waste.utn.models.enums.EstadoOperativo.OPERATIVO);
        }
        
        contenedor.setConActivo(true);
        return contenedorRepository.save(contenedor);
    }

    @Transactional
    public Contenedor actualizarContenedor(@NonNull Integer id, Contenedor datosNuevos) {
        Contenedor contenedor = contenedorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Contenedor no encontrado"));

        contenedor.setConCodigo(datosNuevos.getConCodigo());
        contenedor.setConFacultad(datosNuevos.getConFacultad());
        contenedor.setConTipoResiduo(datosNuevos.getConTipoResiduo());
        contenedor.setConCapacidadLitros(datosNuevos.getConCapacidadLitros());
        contenedor.setConDescripcionUbicacion(datosNuevos.getConDescripcionUbicacion());
        contenedor.setConLatitud(datosNuevos.getConLatitud());
        contenedor.setConLongitud(datosNuevos.getConLongitud());
        contenedor.setConEstadoOperativo(datosNuevos.getConEstadoOperativo());
        
        return contenedorRepository.save(contenedor);
    }

    @SuppressWarnings("null")
    @Transactional
    public void eliminarContenedor(Integer id) {
        Contenedor contenedor = contenedorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Contenedor no encontrado"));

        contenedor.setConActivo(false);
        contenedor.setConEstadoOperativo(EstadoOperativo.FUERA_DE_SERVICIO); 
        
        contenedorRepository.save(contenedor);
    }

    @Transactional(readOnly = true)
    public List<Contenedor> listarInactivos() {
        return contenedorRepository.findByConActivoFalse();
    }

    @Transactional
    public Contenedor reactivarContenedor(@NonNull Integer id) {
        Contenedor contenedor = contenedorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Contenedor no encontrado"));

        contenedor.setConActivo(true);
        contenedor.setConNivelLlenadoPct(0);
        contenedor.setConEstadoLlenado(com.smart_waste.utn.models.enums.EstadoLlenado.VACIO);
        contenedor.setConEstadoOperativo(com.smart_waste.utn.models.enums.EstadoOperativo.OPERATIVO);
        
        return contenedorRepository.save(contenedor);
    }
}