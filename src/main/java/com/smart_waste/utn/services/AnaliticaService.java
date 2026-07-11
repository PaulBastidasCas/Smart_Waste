package com.smart_waste.utn.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.views.VEstadoContenedores;
import com.smart_waste.utn.models.views.VImpactoSocioeconomico;
import com.smart_waste.utn.models.views.VKpiFacultad;
import com.smart_waste.utn.repositories.viewsRepositories.VEstadoContenedoresRepository;
import com.smart_waste.utn.repositories.viewsRepositories.VImpactoSocioeconomicoRepository;
import com.smart_waste.utn.repositories.viewsRepositories.VKpiFacultadRepository;

@Service
public class AnaliticaService {

    private final VEstadoContenedoresRepository estadoContenedoresRepository;
    private final VKpiFacultadRepository kpiFacultadRepository;
    private final VImpactoSocioeconomicoRepository impactoSocioeconomicoRepository;

    public AnaliticaService(VEstadoContenedoresRepository estadoContenedoresRepository,
                            VKpiFacultadRepository kpiFacultadRepository,
                            VImpactoSocioeconomicoRepository impactoSocioeconomicoRepository) {
        this.estadoContenedoresRepository = estadoContenedoresRepository;
        this.kpiFacultadRepository = kpiFacultadRepository;
        this.impactoSocioeconomicoRepository = impactoSocioeconomicoRepository;
    }

    @Transactional(readOnly = true)
    public List<VEstadoContenedores> obtenerEstadoActualContenedores() {
        return estadoContenedoresRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<VKpiFacultad> obtenerKpisPorFacultad() {
        return kpiFacultadRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<VImpactoSocioeconomico> obtenerImpactoSocioeconomicoGlobal() {
        return impactoSocioeconomicoRepository.findAll();
    }
}
