package com.smart_waste.utn.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.AlertaSistema;
import com.smart_waste.utn.repositories.AlertaSistemaRepository;

@Service
public class AlertaService {
    private final AlertaSistemaRepository alertaRepository;

    public AlertaService(AlertaSistemaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    @Transactional(readOnly = true)
    public List<AlertaSistema> obtenerTodasLasAlertas() {
        return alertaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<AlertaSistema> obtenerAlertaPorId(Long id) {
        return alertaRepository.findById(Objects.requireNonNull(id));
    }

    @Transactional
    public AlertaSistema actualizarAlerta(AlertaSistema alerta) {
        return alertaRepository.save(Objects.requireNonNull(alerta, "La alerta no puede ser nula"));
    }
}
