package com.smart_waste.utn.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart_waste.utn.models.Facultad;
import com.smart_waste.utn.models.Rol;
import com.smart_waste.utn.models.TipoIdentificacion;
import com.smart_waste.utn.models.TipoResiduo;
import com.smart_waste.utn.services.CatalogoService;

@RestController
@RequestMapping("/api/catalogos")
public class CatalogoController {

    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService){
        this.catalogoService = catalogoService;
    }

    @GetMapping("/facultades")
    public ResponseEntity<List<Facultad>> listarFacultades() {
        return ResponseEntity.ok(catalogoService.listarFacultades());
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.ok(catalogoService.listarRoles());
    }

    @GetMapping("/tipos-identificacion")
    public ResponseEntity<List<TipoIdentificacion>> listarTiposIdentificacion() {
        return ResponseEntity.ok(catalogoService.listarTiposIdentificacion());
    }

    @GetMapping("/tipos-residuo")
    public ResponseEntity<List<TipoResiduo>> listarTiposResiduo() {
        return ResponseEntity.ok(catalogoService.listarTiposResiduo());
    }
}
