package com.smart_waste.utn.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smart_waste.utn.models.Insignia;
import com.smart_waste.utn.models.RankingMensual;
import com.smart_waste.utn.models.RetoComunitario;
import com.smart_waste.utn.models.UsuarioInsignia;
import com.smart_waste.utn.services.GamificacionService;

@RestController
@RequestMapping("/api/gamificacion")

public class GamificacionController {
private final GamificacionService gamificacionService;

    public GamificacionController(GamificacionService gamificacionService) {
        this.gamificacionService = gamificacionService;
    }

    @GetMapping("/insignias")
    public ResponseEntity<List<Insignia>> obtenerInsignias() {
        return ResponseEntity.ok(gamificacionService.obtenerTodasLasInsignias());
    }

    @PostMapping("/insignias")
    public ResponseEntity<Insignia> crearInsignia(@RequestBody Insignia insignia) {
        return ResponseEntity.ok(gamificacionService.guardarInsignia(insignia));
    }

    @GetMapping("/retos")
    public ResponseEntity<List<RetoComunitario>> obtenerRetos() {
        return ResponseEntity.ok(gamificacionService.obtenerTodosLosRetos());
    }

    @PostMapping("/retos")
    public ResponseEntity<RetoComunitario> crearReto(@RequestBody RetoComunitario reto) {
        return ResponseEntity.ok(gamificacionService.guardarReto(reto));
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingMensual>> obtenerRanking() {
        return ResponseEntity.ok(gamificacionService.obtenerRanking());
    }

    @PostMapping("/usuarios-insignias")
    public ResponseEntity<UsuarioInsignia> otorgarInsignia(@RequestBody UsuarioInsignia asignacion) {
        return ResponseEntity.ok(gamificacionService.otorgarInsigniaAUsuario(asignacion));
    }
}
