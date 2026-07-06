package com.smart_waste.utn.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart_waste.utn.models.Contenedor;
import com.smart_waste.utn.models.Facultad;
import com.smart_waste.utn.models.TipoResiduo;
import com.smart_waste.utn.services.InfraestructuraService;

@RestController
@RequestMapping("/api/infraestructura")
public class InfraestructuraController {
    private final InfraestructuraService infraestructuraService;

    public InfraestructuraController(InfraestructuraService infraestructuraService) {
        this.infraestructuraService = infraestructuraService;
    }

    // Facultades
    @GetMapping("/facultades")
    public ResponseEntity<List<Facultad>> obtenerFacultades() {
        return ResponseEntity.ok(infraestructuraService.obtenerTodasLasFacultades());
    }

    @PostMapping("/facultades")
    public ResponseEntity<Facultad> crearFacultad(@RequestBody @NonNull Facultad facultad) {
        return ResponseEntity.ok(infraestructuraService.guardarFacultad(facultad));
    }

    // Tipos de Residuo
    @GetMapping("/tipos-residuo")
    public ResponseEntity<List<TipoResiduo>> obtenerTiposResiduo() {
        return ResponseEntity.ok(infraestructuraService.obtenerTodosLosTiposResiduo());
    }

    @PostMapping("/tipos-residuo")
    public ResponseEntity<TipoResiduo> crearTipoResiduo(@RequestBody @NonNull TipoResiduo tipoResiduo) {
        return ResponseEntity.ok(infraestructuraService.guardarTipoResiduo(tipoResiduo));
    }

    // Contenedores
    @GetMapping("/contenedores")
    public ResponseEntity<List<Contenedor>> obtenerContenedores() {
        return ResponseEntity.ok(infraestructuraService.obtenerTodosLosContenedores());
    }

    @PostMapping("/contenedores")
    public ResponseEntity<Contenedor> crearContenedor(@RequestBody @NonNull Contenedor contenedor) {
        return ResponseEntity.ok(infraestructuraService.guardarContenedor(contenedor));
    }
}
