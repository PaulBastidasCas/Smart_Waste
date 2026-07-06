package com.smart_waste.utn.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smart_waste.utn.models.AlertaSistema;
import com.smart_waste.utn.services.AlertaService;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {
    private final AlertaService alertaService;

    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    @GetMapping
    public ResponseEntity<List<AlertaSistema>> obtenerAlertas() {
        return ResponseEntity.ok(alertaService.obtenerTodasLasAlertas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaSistema> obtenerAlerta(@PathVariable Long id) {
        return alertaService.obtenerAlertaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertaSistema> actualizarAlerta(@PathVariable Long id, @RequestBody AlertaSistema alerta) {
        alerta.setId(id); 
        return ResponseEntity.ok(alertaService.actualizarAlerta(alerta));
    }
}
