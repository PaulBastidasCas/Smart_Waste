package com.smart_waste.utn.controllers;

import com.smart_waste.utn.models.Contenedor;
import com.smart_waste.utn.services.ContenedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contenedores")
public class ContenedorController {

    private final ContenedorService contenedorService;

    public ContenedorController(ContenedorService contenedorService) {
        this.contenedorService = contenedorService;
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Contenedor>> listarActivos() {
        return ResponseEntity.ok(contenedorService.listarActivos());
    }

    @GetMapping("/facultad/{facultadId}")
    public ResponseEntity<List<Contenedor>> listarPorFacultad(@PathVariable Integer facultadId) {
        return ResponseEntity.ok(contenedorService.listarPorFacultad(facultadId));
    }

    @PostMapping("/{id}/simular-nivel")
    public ResponseEntity<Void> simularLecturaNivel(
            @PathVariable @NonNull Integer id, 
            @RequestBody Map<String, Integer> payload) {
        
        Integer nuevoNivelPct = payload.get("nuevoNivelPct");
        if (nuevoNivelPct == null) {
            throw new IllegalArgumentException("El nivel de llenado es obligatorio");
        }
        
        contenedorService.simularLecturaNivel(id, nuevoNivelPct);
        return ResponseEntity.ok().build();
    }
}