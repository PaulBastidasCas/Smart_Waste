package com.smart_waste.utn.controllers;

import com.smart_waste.utn.models.views.VEstadoContenedores;
import com.smart_waste.utn.models.views.VImpactoSocioeconomico;
import com.smart_waste.utn.models.views.VKpiFacultad;
import com.smart_waste.utn.services.AnaliticaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analiticas")
public class AnaliticaController {

    private final AnaliticaService analiticaService;

    public AnaliticaController(AnaliticaService analiticaService) {
        this.analiticaService = analiticaService;
    }

    @GetMapping("/estado-contenedores")
    public ResponseEntity<List<VEstadoContenedores>> obtenerEstadoContenedores() {
        return ResponseEntity.ok(analiticaService.obtenerEstadoActualContenedores());
    }

    @GetMapping("/kpis-facultad")
    public ResponseEntity<List<VKpiFacultad>> obtenerKpisFacultad() {
        return ResponseEntity.ok(analiticaService.obtenerKpisPorFacultad());
    }

    @GetMapping("/impacto-socioeconomico")
    public ResponseEntity<List<VImpactoSocioeconomico>> obtenerImpactoGlobal() {
        return ResponseEntity.ok(analiticaService.obtenerImpactoSocioeconomicoGlobal());
    }
}
