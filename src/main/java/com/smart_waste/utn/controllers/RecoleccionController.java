package com.smart_waste.utn.controllers;

import com.smart_waste.utn.models.RegistroRecoleccion;
import com.smart_waste.utn.services.RecoleccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recolecciones")
public class RecoleccionController {

    private final RecoleccionService recoleccionService;

    public RecoleccionController(RecoleccionService recoleccionService) {
        this.recoleccionService = recoleccionService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarRecoleccion(@RequestBody Map<String, Object> payload, Principal principal) {
        try {
            RegistroRecoleccion registro = recoleccionService.procesarRegistroDesdeMap(payload, principal.getName());
            String nombreCompleto = registro.getRegEncargado().getUsuNombre() + " " + registro.getRegEncargado().getUsuApellido();

            return ResponseEntity.ok().body(Map.of(
                    "mensaje", "Recolección registrada exitosamente",
                    "registroId", registro.getRegId(),
                    "encargadoNombre", nombreCompleto 
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    private Map<String, Object> mapearRegistro(RegistroRecoleccion r) {
        Map<String, Object> map = new HashMap<>();
        map.put("regId", r.getRegId());
        map.put("regFechaHoraRegistro", r.getRegFechaHoraRegistro());
        map.put("regEstadoRegistro", r.getRegEstadoRegistro());
        map.put("regNivelAntesPct", r.getRegNivelAntesPct());
        map.put("regNivelDespuesPct", r.getRegNivelDespuesPct());
        map.put("regPesoEstimadoKg", r.getRegPesoEstimadoKg());
        map.put("regTieneClasificacionErronea", r.getRegTieneClasificacionErronea());
        map.put("regEstadoLLenado", r.getRegEstadoLLenado());
        map.put("regDescripcionError", r.getRegDescripcionError());
        map.put("regObservaciones", r.getRegObservaciones());
        
        if (r.getRegContenedor() != null) {
            map.put("regContenedor", Map.of(
                "conCodigo", r.getRegContenedor().getConCodigo(),
                "conDescripcionUbicacion", r.getRegContenedor().getConDescripcionUbicacion()
            ));
        }
        if (r.getRegEncargado() != null) {
            map.put("regEncargado", Map.of(
                "usuNombre", r.getRegEncargado().getUsuNombre(),
                "usuApellido", r.getRegEncargado().getUsuApellido()
            ));
        }
        return map;
    }

    @GetMapping("/ultima")
    public ResponseEntity<Map<String, Object>> obtenerUltimaGlobal() {
        RegistroRecoleccion r = recoleccionService.obtenerUltimaRecoleccionGlobalDetalle();
        if (r == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(mapearRegistro(r));
    }

    @GetMapping("/contenedor/{contenedorId}")
    public ResponseEntity<List<Map<String, Object>>> listarPorContenedor(@PathVariable Integer contenedorId) {
        List<RegistroRecoleccion> registros = recoleccionService.listarPorContenedor(contenedorId);
        List<Map<String, Object>> respuesta = registros.stream()
            .map(this::mapearRegistro)
            .collect(Collectors.toList());
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/facultad/{facultadId}")
    public ResponseEntity<Map<String, Object>> listarPorFacultad(@PathVariable Integer facultadId) {
        return ResponseEntity.ok(recoleccionService.obtenerEstadisticasFacultad(facultadId));
    }

    @GetMapping("/recientes-globales")
    public ResponseEntity<List<Map<String, Object>>> listarRecientesGlobales() {
        return ResponseEntity.ok(recoleccionService.obtenerRecientesGlobales());
    }

    @GetMapping("/metricas")
    public ResponseEntity<Map<String, Object>> obtenerMetricasDashboard() {
        return ResponseEntity.ok(recoleccionService.obtenerMetricasDashboard());
    }
}