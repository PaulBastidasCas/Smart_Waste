package com.smart_waste.utn.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.RankingFacultadMensual;
import com.smart_waste.utn.models.RetoComunitario;
import com.smart_waste.utn.models.views.VFeedInsigniasUsuario;
import com.smart_waste.utn.repositories.RankingFacultadMensualRepository;
import com.smart_waste.utn.repositories.RetoComunitarioRepository;
import com.smart_waste.utn.repositories.viewsRepositories.VFeedInsigniasUsuarioRepository;

@Service
public class GamificacionService {

    private final RetoComunitarioRepository retoComunitarioRepository;
    private final RankingFacultadMensualRepository rankingRepository;
    private final VFeedInsigniasUsuarioRepository feedInsigniasRepository;

    public GamificacionService(RetoComunitarioRepository retoComunitarioRepository,
                               RankingFacultadMensualRepository rankingRepository,
                               VFeedInsigniasUsuarioRepository feedInsigniasRepository) {
        this.retoComunitarioRepository = retoComunitarioRepository;
        this.rankingRepository = rankingRepository;
        this.feedInsigniasRepository = feedInsigniasRepository;
    }

    @Transactional(readOnly = true)
    public List<RetoComunitario> listarRetosActivos() {
        return retoComunitarioRepository.findByRetEstado("ACTIVO");
    }

    @Transactional(readOnly = true)
    public List<RankingFacultadMensual> obtenerRankingMensual(Integer anio, Integer mes) {
        return rankingRepository.findByRanAnioAndRanMesOrderByRanPuntosTotalDesc(anio, mes);
    }

    @Transactional(readOnly = true)
    public List<VFeedInsigniasUsuario> obtenerFeedInsigniasEstudiante(UUID usuarioId) {
        return feedInsigniasRepository.findByUsuId(usuarioId);
    }
}
