package com.smart_waste.utn.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart_waste.utn.models.vistas.VEstadoContenedor;
import com.smart_waste.utn.models.vistas.VImpactoSocioeconomico;
import com.smart_waste.utn.models.vistas.VKpiFacultad;
import com.smart_waste.utn.services.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/estado-contenedores")
    public ResponseEntity<List<VEstadoContenedor>> estadoContenedores() {
        return ResponseEntity.ok(dashboardService.obtenerEstadoContenedores());
    }

    @GetMapping("/kpis-facultades")
    public ResponseEntity<List<VKpiFacultad>> kpisFacultades() {
        return ResponseEntity.ok(dashboardService.obtenerKpisFacultades());
    }

    @GetMapping("/impacto-socioeconomico")
    public ResponseEntity<List<VImpactoSocioeconomico>> impactoSocioeconomico() {
        return ResponseEntity.ok(dashboardService.obtenerImpactoSocioeconomico());
    }
}
