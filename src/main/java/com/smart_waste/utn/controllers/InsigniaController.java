package com.smart_waste.utn.controllers;

import com.smart_waste.utn.models.Insignia;
import com.smart_waste.utn.models.dto.ActualizarIconoRequest;
import com.smart_waste.utn.models.dto.InsigniaRequest;
import com.smart_waste.utn.services.InsigniaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/insignias")
public class InsigniaController {

    private final InsigniaService insigniaService;

    public InsigniaController(InsigniaService insigniaService) {
        this.insigniaService = insigniaService;
    }

    @GetMapping
    public ResponseEntity<List<Insignia>> listarTodas() {
        return ResponseEntity.ok(insigniaService.listarTodas());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearInsignia(@Valid @RequestBody InsigniaRequest request) {
        Insignia insignia = new Insignia();
        insignia.setInsNombre(request.getInsNombre());
        insignia.setInsDescripcion(request.getInsDescripcion());
        insignia.setInsIconoBase64(request.getInsIconoBase64());
        insignia.setInsColorHex(request.getInsColorHex());
        insignia.setInsCriterioTipo(request.getInsCriterioTipo());
        insignia.setInsCriterioValor(request.getInsCriterioValor());

        Insignia nuevaInsignia = insigniaService.crearInsignia(insignia);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "mensaje", "Insignia creada exitosamente",
            "insigniaId", nuevaInsignia.getInsId()
        ));
    }

    @PatchMapping("/{id}/icono")
    public ResponseEntity<Map<String, Object>> actualizarIcono(
            @PathVariable @NonNull Integer id, 
            @Valid @RequestBody ActualizarIconoRequest payload) {
        
        Insignia actualizada = insigniaService.actualizarIcono(id, payload.getIconoBase64());
        return ResponseEntity.ok(Map.of(
            "mensaje", "Icono de insignia actualizado exitosamente",
            "insigniaId", actualizada.getInsId()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarInsignia(
            @PathVariable @NonNull Integer id, 
            @Valid @RequestBody InsigniaRequest request) {
        
        Insignia insignia = new Insignia();
        insignia.setInsNombre(request.getInsNombre());
        insignia.setInsDescripcion(request.getInsDescripcion());
        insignia.setInsIconoBase64(request.getInsIconoBase64());
        insignia.setInsColorHex(request.getInsColorHex());
        insignia.setInsCriterioTipo(request.getInsCriterioTipo());
        insignia.setInsCriterioValor(request.getInsCriterioValor());

        Insignia actualizada = insigniaService.actualizarInsignia(id, insignia);
        return ResponseEntity.ok(Map.of(
            "mensaje", "Insignia actualizada exitosamente",
            "insigniaId", actualizada.getInsId()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInsignia(@PathVariable @NonNull Integer id) {
        insigniaService.eliminarInsignia(id);
        return ResponseEntity.noContent().build();
    }
}