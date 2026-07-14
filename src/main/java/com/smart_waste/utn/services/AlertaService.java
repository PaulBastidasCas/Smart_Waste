package com.smart_waste.utn.services;

import com.smart_waste.utn.models.AlertaSistema;
import com.smart_waste.utn.repositories.AlertaSistemaRepository;
import com.smart_waste.utn.exceptions.ResourceNotFoundException;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertaService {

    private final AlertaSistemaRepository alertaSistemaRepository;

    public AlertaService(AlertaSistemaRepository alertaSistemaRepository) {
        this.alertaSistemaRepository = alertaSistemaRepository;
    }

    @Transactional(readOnly = true)
    public List<AlertaSistema> listarAlertasActivas() {
        return alertaSistemaRepository.findByAleLeidaFalse();
    }

    @Transactional
    public void resolverAlerta(@NonNull Long alertaId) { 
        AlertaSistema alerta = alertaSistemaRepository.findById(alertaId)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada"));
        
        alerta.setAleLeida(true);
        alerta.setAleLeidaEn(LocalDateTime.now());
        
        alertaSistemaRepository.save(alerta);
    }
}