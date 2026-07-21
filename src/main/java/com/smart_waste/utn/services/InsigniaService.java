package com.smart_waste.utn.services;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.Insignia;
import com.smart_waste.utn.repositories.InsigniaRepository;
import com.smart_waste.utn.exceptions.ResourceNotFoundException;

@Service
public class InsigniaService {

    private final InsigniaRepository insigniaRepository;

    public InsigniaService(InsigniaRepository insigniaRepository) {
        this.insigniaRepository = insigniaRepository;
    }

    @Transactional(readOnly = true)
    public List<Insignia> listarTodas() {
        return insigniaRepository.findAll();
    }

    @Transactional
    public Insignia crearInsignia(@NonNull Insignia insignia) {
        return insigniaRepository.save(insignia);
    }

   @Transactional
    public Insignia actualizarIcono(@NonNull Integer id, String iconoBase64) {
        Insignia insignia = insigniaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Insignia no encontrada"));
        
        insignia.setInsIconoBase64(iconoBase64);
        return insigniaRepository.save(insignia);
    }

    @Transactional
    public Insignia actualizarInsignia(@NonNull Integer id, Insignia datosNuevos) {
        Insignia insignia = insigniaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Insignia no encontrada"));
        
        insignia.setInsNombre(datosNuevos.getInsNombre());
        insignia.setInsDescripcion(datosNuevos.getInsDescripcion());
        insignia.setInsIconoBase64(datosNuevos.getInsIconoBase64());
        insignia.setInsColorHex(datosNuevos.getInsColorHex());
        insignia.setInsCriterioTipo(datosNuevos.getInsCriterioTipo());
        insignia.setInsCriterioValor(datosNuevos.getInsCriterioValor());
        
        return insigniaRepository.save(insignia);
    }

    @SuppressWarnings("null")
    @Transactional
    public void eliminarInsignia(@NonNull Integer id) {
        Insignia insignia = insigniaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Insignia no encontrada"));
        insigniaRepository.delete(insignia);
    }
}