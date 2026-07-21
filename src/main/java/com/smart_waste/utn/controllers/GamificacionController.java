package com.smart_waste.utn.controllers;

import com.smart_waste.utn.models.RankingFacultadMensual;
import com.smart_waste.utn.models.RetoComunitario;
import com.smart_waste.utn.models.RegistroRecoleccion;
import com.smart_waste.utn.models.Usuario;
import com.smart_waste.utn.models.views.VFeedInsigniasUsuario;
import com.smart_waste.utn.services.GamificacionService;
import com.smart_waste.utn.services.UsuarioService;
import com.smart_waste.utn.repositories.RegistroRecoleccionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gamificacion")
public class GamificacionController {

    private final GamificacionService gamificacionService;
    private final RegistroRecoleccionRepository recoleccionRepository;
    private final UsuarioService usuarioService;

    public GamificacionController(GamificacionService gamificacionService, 
                                  RegistroRecoleccionRepository recoleccionRepository,
                                  UsuarioService usuarioService) {
        this.gamificacionService = gamificacionService;
        this.recoleccionRepository = recoleccionRepository;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/retos-activos")
    public ResponseEntity<List<RetoComunitario>> listarRetosActivos() {
        return ResponseEntity.ok(gamificacionService.listarRetosActivos());
    }

    @SuppressWarnings("null")
    @GetMapping("/estudiante/desafios-activos")
    public ResponseEntity<List<Map<String, Object>>> listarDesafiosActivos(
            Principal principal, 
            @RequestParam(value = "correo", required = false) String correoParam) {
        
        String email = usuarioService.resolverCorreoUsuario(principal, correoParam);
        Usuario usuario = usuarioService.obtenerPorCorreo(email);
        Integer facId = (usuario.getUsuFacultad() != null) ? usuario.getUsuFacultad().getFacId() : null;

        return ResponseEntity.ok(gamificacionService.obtenerDesafiosMapeadosPorFacultad(facId));
    }

    @GetMapping("/ranking-mensual")
    public ResponseEntity<List<RankingFacultadMensual>> obtenerRanking(@RequestParam Integer anio, @RequestParam Integer mes) {
        return ResponseEntity.ok(gamificacionService.obtenerRankingMensual(anio, mes));
    }

    @GetMapping("/ranking-facultades")
    public ResponseEntity<List<java.util.Map<String, Object>>> obtenerRankingFacultades() {
        return ResponseEntity.ok(gamificacionService.obtenerRankingFacultadesCompletados());
    }

    @GetMapping("/feed-insignias/{usuarioId}")
    public ResponseEntity<List<VFeedInsigniasUsuario>> obtenerFeedInsignias(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(gamificacionService.obtenerFeedInsigniasEstudiante(usuarioId));
    }

    @GetMapping("/reportes-errores")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Map<String, Object>>> listarReportes() {
        List<RegistroRecoleccion> reportes = recoleccionRepository.findByRegTieneClasificacionErroneaTrue();

        List<Map<String, Object>> resultado = reportes.stream().map(r -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("regId", r.getRegId());
            dto.put("regDescripcionError", r.getRegDescripcionError());
            dto.put("regFechaHoraRegistro", r.getRegFechaHoraRegistro());

            Map<String, Object> contenedorDto = new HashMap<>();
            if (r.getRegContenedor() != null) {
                contenedorDto.put("conId", r.getRegContenedor().getConId());
                contenedorDto.put("conCodigo", r.getRegContenedor().getConCodigo());

                Map<String, Object> facultadDto = new HashMap<>();
                if (r.getRegContenedor().getConFacultad() != null) {
                    facultadDto.put("facId", r.getRegContenedor().getConFacultad().getFacId());
                    facultadDto.put("facNombre", r.getRegContenedor().getConFacultad().getFacNombre());
                }
                contenedorDto.put("conFacultad", facultadDto);
            }
            dto.put("regContenedor", contenedorDto);

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/retos")
    public ResponseEntity<String> crearReto(@RequestBody RetoComunitario nuevoReto) {
        gamificacionService.guardarReto(nuevoReto);
        return ResponseEntity.ok("Desafío creado correctamente");
    }
}