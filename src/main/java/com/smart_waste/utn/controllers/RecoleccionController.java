package com.smart_waste.utn.controllers;

import com.smart_waste.utn.models.RegistroRecoleccion;
import com.smart_waste.utn.services.RecoleccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recolecciones")
public class RecoleccionController {

    private final RecoleccionService recoleccionService;

    public RecoleccionController(RecoleccionService recoleccionService) {
        this.recoleccionService = recoleccionService;
    }

    @PostMapping
    public ResponseEntity<RegistroRecoleccion> registrarVaciado(@RequestBody @NonNull RegistroRecoleccion registro) {
        return ResponseEntity.ok(recoleccionService.registrarVaciado(registro));
    }

    @GetMapping("/contenedor/{contenedorId}")
    public ResponseEntity<List<RegistroRecoleccion>> listarPorContenedor(@PathVariable Integer contenedorId) {
        return ResponseEntity.ok(recoleccionService.listarPorContenedor(contenedorId));
    }
}
