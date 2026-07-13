package com.smart_waste.utn.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.Contenedor;
import com.smart_waste.utn.models.RegistroRecoleccion;
import com.smart_waste.utn.models.Usuario;
import com.smart_waste.utn.models.enums.EstadoLlenado;
import com.smart_waste.utn.repositories.ContenedorRepository;
import com.smart_waste.utn.repositories.RegistroRecoleccionRepository;
import com.smart_waste.utn.repositories.UsuarioRepository;

@Service
public class RecoleccionService {

    private final RegistroRecoleccionRepository registroRecoleccionRepository;
    private final ContenedorRepository contenedorRepository;
    private final UsuarioRepository usuarioRepository;

    public RecoleccionService(RegistroRecoleccionRepository registroRecoleccionRepository, 
                              ContenedorRepository contenedorRepository,
                              UsuarioRepository usuarioRepository) {
        this.registroRecoleccionRepository = registroRecoleccionRepository;
        this.contenedorRepository = contenedorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public RegistroRecoleccion registrarVaciado(@NonNull Integer contenedorId, Double cantidadRecolectada, String observaciones) {

        String correoAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario encargado = usuarioRepository.findByUsuCorreo(correoAutenticado) 
            .orElseThrow(() -> new RuntimeException("Usuario encargado no encontrado en la base de datos"));

        Contenedor contenedor = contenedorRepository.findById(contenedorId)
            .orElseThrow(() -> new RuntimeException("Contenedor no encontrado"));

        double capacidad = contenedor.getConCapacidadLitros().doubleValue();
        int porcentajeActual = contenedor.getConNivelLlenadoPct();

        double volumenActual = (capacidad * porcentajeActual) / 100.0;
        double nuevoVolumen = Math.max(0, volumenActual - cantidadRecolectada);
        int nuevoPorcentaje = (int) Math.round((nuevoVolumen / capacidad) * 100.0);

        EstadoLlenado nuevoEstado = EstadoLlenado.VACIO;
        if (nuevoPorcentaje >= 80) nuevoEstado = EstadoLlenado.CRITICO;
        else if (nuevoPorcentaje >= 40) nuevoEstado = EstadoLlenado.MEDIO;

        contenedor.setConNivelLlenadoPct(nuevoPorcentaje);
        contenedor.setConEstadoLlenado(nuevoEstado);
        contenedorRepository.save(contenedor);

        RegistroRecoleccion registro = new RegistroRecoleccion();
        registro.setRegContenedor(contenedor);
        registro.setRegEncargado(encargado); 
        
        registro.setRegNivelAntesPct(porcentajeActual); 
        registro.setRegNivelDespuesPct(nuevoPorcentaje); 
        registro.setRegEstadoLLenado(nuevoEstado); 
        
        registro.setRegPesoEstimadoKg(BigDecimal.valueOf(cantidadRecolectada)); 
        registro.setRegObservaciones(observaciones);

        return registroRecoleccionRepository.save(registro);
    }

    @Transactional(readOnly = true)
    public List<RegistroRecoleccion> listarPorContenedor(Integer contenedorId) {
        return registroRecoleccionRepository.findByRegContenedor_ConIdOrderByRegFechaHoraRegistroDesc(contenedorId);
    }
}
