package com.smart_waste.utn.controllers;

import com.smart_waste.utn.models.AlertaSistema;
import com.smart_waste.utn.services.AlertaService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    private final AlertaService alertaService;

    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    @GetMapping("/activas")
    public ResponseEntity<List<AlertaSistema>> listarAlertasActivas() {
        return ResponseEntity.ok(alertaService.listarAlertasActivas());
    }

    @PutMapping("/{alertaId}/resolver")
    public ResponseEntity<Void> resolverAlerta(@PathVariable @NonNull Long alertaId) {
        alertaService.resolverAlerta(alertaId);
        return ResponseEntity.ok().build();
    }
}
