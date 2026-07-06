package com.smart_waste.utn.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import com.smart_waste.utn.models.RegistroRecoleccion;
import com.smart_waste.utn.services.RecoleccionService;

@RestController
@RequestMapping("/api/recolecciones")
public class RecoleccionController {

    private final RecoleccionService recoleccionService;

    public RecoleccionController(RecoleccionService recoleccionService) {
        this.recoleccionService = recoleccionService;
    }

    @GetMapping
    public ResponseEntity<List<RegistroRecoleccion>> obtenerRecolecciones() {
        return ResponseEntity.ok(recoleccionService.obtenerTodasLasRecolecciones());
    }

    @PostMapping
    public ResponseEntity<RegistroRecoleccion> registrarRecoleccion(@RequestBody @NonNull RegistroRecoleccion registro) {
        return ResponseEntity.ok(recoleccionService.registrarRecoleccion(registro));
    }
}
