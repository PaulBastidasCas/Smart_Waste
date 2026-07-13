package com.smart_waste.utn.controllers;

import com.smart_waste.utn.models.RegistroRecoleccion;
import com.smart_waste.utn.services.RecoleccionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recolecciones")
public class RecoleccionController {

    private final RecoleccionService recoleccionService;

    public RecoleccionController(RecoleccionService recoleccionService) {
        this.recoleccionService = recoleccionService;
    }

    @SuppressWarnings("null")
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarRecoleccion(@RequestBody Map<String, Object> payload) {
        try {
            Integer contenedorId = (Integer) payload.get("contenedorId");
            
            Double pesoRecolectado = Double.valueOf(payload.get("pesoRecolectado").toString());
            
            String observaciones = (String) payload.getOrDefault("observaciones", "");

            RegistroRecoleccion registro = recoleccionService.registrarVaciado(contenedorId, pesoRecolectado, observaciones);

            return ResponseEntity.ok().body(Map.of(
                    "mensaje", "Recolección registrada exitosamente",
                    "registroId", registro.getRegId()
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Ocurrió un error interno al procesar la recolección."
            ));
        }
    }

    @GetMapping("/contenedor/{contenedorId}")
    public ResponseEntity<List<RegistroRecoleccion>> listarPorContenedor(@PathVariable Integer contenedorId) {
        return ResponseEntity.ok(recoleccionService.listarPorContenedor(contenedorId));
    }
}
