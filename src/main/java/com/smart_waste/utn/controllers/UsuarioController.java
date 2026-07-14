package com.smart_waste.utn.controllers;

import com.smart_waste.utn.models.Usuario;
import com.smart_waste.utn.models.dto.ActualizarFotoRequest;
import com.smart_waste.utn.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable @NonNull UUID id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    @GetMapping("/facultad/{facultadId}")
    public ResponseEntity<List<Usuario>> listarPorFacultad(@PathVariable Integer facultadId) {
        return ResponseEntity.ok(usuarioService.listarPorFacultad(facultadId));
    }

    @PatchMapping("/{id}/foto-perfil")
    public ResponseEntity<Map<String, Object>> actualizarFotoPerfil(
            @PathVariable @NonNull UUID id, 
            @Valid @RequestBody ActualizarFotoRequest payload) {
        
        Usuario usuarioActualizado = usuarioService.actualizarFotoPerfil(id, payload.getFotoBase64());
        return ResponseEntity.ok(Map.of(
            "mensaje", "Foto de perfil actualizada exitosamente",
            "usuarioId", usuarioActualizado.getUsuId()
        ));
    }
}