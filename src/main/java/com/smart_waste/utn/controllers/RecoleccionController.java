package com.smart_waste.utn.controllers;

import com.smart_waste.utn.models.RegistroRecoleccion;
import com.smart_waste.utn.models.dto.RecoleccionRequest;
import com.smart_waste.utn.services.RecoleccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recolecciones")
public class RecoleccionController {

    private final RecoleccionService recoleccionService;

    public RecoleccionController(RecoleccionService recoleccionService) {
        this.recoleccionService = recoleccionService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Map<String, Object>> registrarRecoleccion(
            @Valid @RequestBody RecoleccionRequest payload, 
            Principal principal) {
        
        RegistroRecoleccion registro = recoleccionService.registrarVaciado(
                payload.getContenedorId(), 
                payload.getPesoRecolectado(), 
                payload.getObservaciones(), 
                payload.getRegFotoBase64(),
                payload.getTieneClasificacionErronea(), 
                payload.getDescripcionError(),
                principal.getName() 
        );

        String nombreCompleto = registro.getRegEncargado().getUsuNombre() + " " + registro.getRegEncargado().getUsuApellido();

        return ResponseEntity.ok().body(Map.of(
                "mensaje", "Recolección registrada exitosamente",
                "registroId", registro.getRegId(),
                "encargadoNombre", nombreCompleto 
        ));
    }

    @GetMapping("/contenedor/{contenedorId}")
    public ResponseEntity<List<RegistroRecoleccion>> listarPorContenedor(@PathVariable Integer contenedorId) {
        return ResponseEntity.ok(recoleccionService.listarPorContenedor(contenedorId));
    }
}
