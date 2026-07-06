package com.smart_waste.utn.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.vistas.VEstadoContenedor;
import com.smart_waste.utn.models.vistas.VImpactoSocioeconomico;
import com.smart_waste.utn.models.vistas.VKpiFacultad;
import com.smart_waste.utn.repositories.vistasRepositories.VEstadoContenedorRepository;
import com.smart_waste.utn.repositories.vistasRepositories.VImpactoSocioeconomicoRepository;
import com.smart_waste.utn.repositories.vistasRepositories.VKpiFacultadRepository;

@Service
public class DashboardService {
    private final VEstadoContenedorRepository estadoContenedorRepo;
    private final VKpiFacultadRepository kpiFacultadRepo;
    private final VImpactoSocioeconomicoRepository impactoSocioeconomicoRepo;

    public DashboardService(VEstadoContenedorRepository estadoContenedorRepo,
            VKpiFacultadRepository kpiFacultadRepo,
            VImpactoSocioeconomicoRepository impactoSocioeconomicoRepo) {
        this.estadoContenedorRepo = estadoContenedorRepo;
        this.kpiFacultadRepo = kpiFacultadRepo;
        this.impactoSocioeconomicoRepo = impactoSocioeconomicoRepo;
    }

    @Transactional(readOnly = true)
    public List<VEstadoContenedor> obtenerEstadoContenedores() {
        return estadoContenedorRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<VKpiFacultad> obtenerKpisFacultades() {
        return kpiFacultadRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<VImpactoSocioeconomico> obtenerImpactoSocioeconomico() {
        return impactoSocioeconomicoRepo.findAll();
    }
}
