package com.smart_waste.utn.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.RankingFacultadMensual;
import com.smart_waste.utn.models.RetoComunitario;
import com.smart_waste.utn.models.Facultad;
import com.smart_waste.utn.models.RegistroRecoleccion;
import com.smart_waste.utn.models.enums.TipoMeta;
import com.smart_waste.utn.models.enums.RetoEstado;
import com.smart_waste.utn.models.views.VFeedInsigniasUsuario;
import com.smart_waste.utn.repositories.RankingFacultadMensualRepository;
import com.smart_waste.utn.repositories.RetoComunitarioRepository;
import com.smart_waste.utn.repositories.FacultadRepository;
import com.smart_waste.utn.repositories.RegistroRecoleccionRepository;
import com.smart_waste.utn.repositories.viewsRepositories.VFeedInsigniasUsuarioRepository;

@Service
public class GamificacionService {

    private final RetoComunitarioRepository retoComunitarioRepository;
    private final RankingFacultadMensualRepository rankingRepository;
    private final VFeedInsigniasUsuarioRepository feedInsigniasRepository;
    private final FacultadRepository facultadRepository;
    private final RegistroRecoleccionRepository recoleccionRepository;

    public GamificacionService(RetoComunitarioRepository retoComunitarioRepository,
                               RankingFacultadMensualRepository rankingRepository,
                               VFeedInsigniasUsuarioRepository feedInsigniasRepository,
                               FacultadRepository facultadRepository,
                               RegistroRecoleccionRepository recoleccionRepository) {
        this.retoComunitarioRepository = retoComunitarioRepository;
        this.rankingRepository = rankingRepository;
        this.feedInsigniasRepository = feedInsigniasRepository;
        this.facultadRepository = facultadRepository;
        this.recoleccionRepository = recoleccionRepository;
    }

    @Transactional(readOnly = true)
    public List<RetoComunitario> listarRetosActivos() {
        return retoComunitarioRepository.findByRetEstado(RetoEstado.ACTIVO);
    }

    @Transactional(readOnly = true)
    public List<RetoComunitario> listarDesafiosActivosPorFacultad(Integer facId) {
        return retoComunitarioRepository.findByRetFacultad_FacIdAndRetEstado(facId, RetoEstado.ACTIVO);
    }

    @Transactional(readOnly = true)
    public List<RetoComunitario> listarDesafiosClasificacionPorFacultad(Integer facId) {
        return retoComunitarioRepository.findByRetFacultad_FacIdAndRetEstadoAndRetTipoMeta(facId, RetoEstado.ACTIVO, TipoMeta.CLASIFICACION);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> obtenerDesafiosMapeadosPorFacultad(Integer facId) {
        if (facId == null) {
            return List.of();
        }
        
        List<RetoComunitario> filtered = listarDesafiosClasificacionPorFacultad(facId);
        
        return filtered.stream().map(reto -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("retId", reto.getRetId());
            dto.put("retTitulo", reto.getRetTitulo());
            dto.put("retDescripcion", reto.getRetDescripcion());
            dto.put("retProgresoPct", reto.getRetProgresoPct());
            dto.put("retMetaKg", reto.getRetMetaKg());
            dto.put("retFechaInicio", reto.getRetFechaInicio());
            dto.put("retFechaFin", reto.getRetFechaFin());
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void actualizarProgresoRetosClasificacion(Integer facultadId, BigDecimal pesoKg, boolean esClasificacionCorrecta) {
        if (facultadId == null || pesoKg == null || !esClasificacionCorrecta) {
            return;
        }

        List<RetoComunitario> retosActivos = retoComunitarioRepository
                .findByRetFacultad_FacIdAndRetEstadoAndRetTipoMeta(facultadId, RetoEstado.ACTIVO, TipoMeta.CLASIFICACION);

        for (RetoComunitario reto : retosActivos) {
            BigDecimal progresoActual = reto.getRetProgresoActualKg() != null ? reto.getRetProgresoActualKg() : BigDecimal.ZERO;
            BigDecimal nuevoProgreso = progresoActual.add(pesoKg);
            reto.setRetProgresoActualKg(nuevoProgreso);

            BigDecimal metaKg = reto.getRetMetaKg();
            if (metaKg != null && metaKg.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal pct = nuevoProgreso
                        .multiply(BigDecimal.valueOf(100))
                        .divide(metaKg, 2, RoundingMode.HALF_UP);

                if (pct.compareTo(BigDecimal.valueOf(100)) > 0) {
                    pct = BigDecimal.valueOf(100);
                }
                reto.setRetProgresoPct(pct);

                if (pct.compareTo(BigDecimal.valueOf(100)) >= 0) {
                    reto.setRetEstado(RetoEstado.COMPLETADO);
                }
            }

            retoComunitarioRepository.save(reto);
        }
    }

    @Transactional(readOnly = true)
    public List<RankingFacultadMensual> obtenerRankingMensual(Integer anio, Integer mes) {
        List<RankingFacultadMensual> dbRanking = rankingRepository.findByRanAnioAndRanMesOrderByRanPuntosTotalDesc(anio, mes);
        if (!dbRanking.isEmpty()) {
            return dbRanking;
        }

        List<Facultad> facultades = facultadRepository.findAll();
        List<RankingFacultadMensual> list = new java.util.ArrayList<>();
        
        for (Facultad f : facultades) {
            List<RegistroRecoleccion> recs = recoleccionRepository.findByRegContenedor_ConFacultad_FacId(f.getFacId());
            double totalKg = recs.stream()
                .filter(r -> r.getRegPesoEstimadoKg() != null)
                .mapToDouble(r -> r.getRegPesoEstimadoKg().doubleValue())
                .sum();
            
            RankingFacultadMensual rank = new RankingFacultadMensual();
            rank.setRanAnio(anio);
            rank.setRanMes(mes);
            rank.setRanFacultad(f);
            rank.setRanKgReciclados(java.math.BigDecimal.valueOf(totalKg));
            rank.setRanPuntosTotal((int) Math.round(totalKg * 15.0) + 340);
            list.add(rank);
        }
        
        list.sort((r1, r2) -> r2.getRanPuntosTotal().compareTo(r1.getRanPuntosTotal()));
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setRanPosicion(i + 1);
        }
        return list;
    }

    @Transactional(readOnly = true)
    public List<VFeedInsigniasUsuario> obtenerFeedInsigniasEstudiante(UUID usuarioId) {
        return feedInsigniasRepository.findByUsuId(usuarioId);
    }

    @Transactional(readOnly = true)
    public List<java.util.Map<String, Object>> obtenerRankingFacultadesCompletados() {
        List<Facultad> facultades = facultadRepository.findAll();
        List<RetoComunitario> todosLosRetos = retoComunitarioRepository.findAll();

        List<java.util.Map<String, Object>> ranking = new java.util.ArrayList<>();
        for (Facultad f : facultades) {
            long countCompletados = todosLosRetos.stream()
                .filter(r -> r.getRetFacultad() != null && r.getRetFacultad().getFacId().equals(f.getFacId()))
                .filter(r -> r.getRetProgresoPct() != null && r.getRetProgresoPct().doubleValue() >= 100.0)
                .count();

            java.util.Map<String, Object> item = new java.util.HashMap<>();
            item.put("facultadId", f.getFacId());
            item.put("facultadNombre", f.getFacNombre());
            item.put("facultadCodigo", f.getFacCodigo());
            item.put("retosCompletados", countCompletados);
            item.put("ranPuntosTotal", countCompletados * 500 + 1000); 
            ranking.add(item);
        }

        ranking.sort((m1, m2) -> {
            int comp = Long.compare((Long) m2.get("retosCompletados"), (Long) m1.get("retosCompletados"));
            if (comp != 0) return comp;
            return ((String) m1.get("facultadCodigo")).compareTo((String) m2.get("facultadCodigo"));
        });

        return ranking;
    }

    @Transactional(readOnly = true)
    public List<RegistroRecoleccion> listarReportesErrores() {
        return recoleccionRepository.findByRegTieneClasificacionErroneaTrue();
    }

    @Transactional
    public void guardarReto(RetoComunitario reto) {
        reto.setRetEstado(RetoEstado.ACTIVO);
        retoComunitarioRepository.save(reto);
    }
}