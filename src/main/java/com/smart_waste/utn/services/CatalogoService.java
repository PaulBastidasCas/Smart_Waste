package com.smart_waste.utn.services;

import com.smart_waste.utn.models.Facultad;
import com.smart_waste.utn.models.Rol;
import com.smart_waste.utn.models.TipoIdentificacion;
import com.smart_waste.utn.models.TipoResiduo;
import com.smart_waste.utn.repositories.FacultadRepository;
import com.smart_waste.utn.repositories.RolRepository;
import com.smart_waste.utn.repositories.TipoIdentificacionRepository;
import com.smart_waste.utn.repositories.TipoResiduoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CatalogoService {

    private final FacultadRepository facultadRepository;
    private final RolRepository rolRepository;
    private final TipoIdentificacionRepository tipoIdentificacionRepository;
    private final TipoResiduoRepository tipoResiduoRepository;

    public CatalogoService(FacultadRepository facultadRepository,
                           RolRepository rolRepository,
                           TipoIdentificacionRepository tipoIdentificacionRepository,
                           TipoResiduoRepository tipoResiduoRepository) {
        this.facultadRepository = facultadRepository;
        this.rolRepository = rolRepository;
        this.tipoIdentificacionRepository = tipoIdentificacionRepository;
        this.tipoResiduoRepository = tipoResiduoRepository;
    }

    public List<Facultad> listarFacultades() {
        return facultadRepository.findAll();
    }

    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    public List<TipoIdentificacion> listarTiposIdentificacion() {
        return tipoIdentificacionRepository.findAll();
    }

    public List<TipoResiduo> listarTiposResiduo() {
        return tipoResiduoRepository.findAll();
    }
}
