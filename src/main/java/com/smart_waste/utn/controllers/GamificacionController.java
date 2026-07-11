package com.smart_waste.utn.controllers;

import com.smart_waste.utn.models.RankingFacultadMensual;
import com.smart_waste.utn.models.RetoComunitario;
import com.smart_waste.utn.models.views.VFeedInsigniasUsuario;
import com.smart_waste.utn.services.GamificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gamificacion")
public class GamificacionController {

    private final GamificacionService gamificacionService;

    public GamificacionController(GamificacionService gamificacionService) {
        this.gamificacionService = gamificacionService;
    }

    @GetMapping("/retos-activos")
    public ResponseEntity<List<RetoComunitario>> listarRetosActivos() {
        return ResponseEntity.ok(gamificacionService.listarRetosActivos());
    }

    @GetMapping("/ranking-mensual")
    public ResponseEntity<List<RankingFacultadMensual>> obtenerRanking(
            @RequestParam Integer anio, 
            @RequestParam Integer mes) {
        return ResponseEntity.ok(gamificacionService.obtenerRankingMensual(anio, mes));
    }

    @GetMapping("/feed-insignias/{usuarioId}")
    public ResponseEntity<List<VFeedInsigniasUsuario>> obtenerFeedInsignias(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(gamificacionService.obtenerFeedInsigniasEstudiante(usuarioId));
    }
}
