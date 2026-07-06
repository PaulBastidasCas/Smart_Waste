package com.smart_waste.utn.services;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.Contenedor;
import com.smart_waste.utn.models.Facultad;
import com.smart_waste.utn.models.TipoResiduo;
import com.smart_waste.utn.repositories.ContenedorRepository;
import com.smart_waste.utn.repositories.FacultadRepository;
import com.smart_waste.utn.repositories.TipoResiduoRepository;

@Service
public class InfraestructuraService {

    private final ContenedorRepository contenedorRepository;
    private final FacultadRepository facultadRepository;
    private final TipoResiduoRepository tipoResiduoRepository;

    public InfraestructuraService(ContenedorRepository contenedorRepository,
                                  FacultadRepository facultadRepository,
                                  TipoResiduoRepository tipoResiduoRepository){
        this.contenedorRepository = contenedorRepository;
        this.facultadRepository = facultadRepository;
        this.tipoResiduoRepository = tipoResiduoRepository;
    }

    @Transactional(readOnly = true)
    public List<Facultad> obtenerTodasLasFacultades(){
        return facultadRepository.findAll();
    }

    @Transactional
    public Facultad guardarFacultad(@NonNull Facultad facultad){
        return facultadRepository.save(facultad);
    }

    @Transactional(readOnly = true)
    public List<TipoResiduo> obtenerTodosLosTiposResiduo(){
        return tipoResiduoRepository.findAll();
    }

    @Transactional
    public TipoResiduo guardarTipoResiduo(@NonNull TipoResiduo tipoResiduo){
        return tipoResiduoRepository.save(tipoResiduo);
    }

    @Transactional(readOnly = true)
    public List<Contenedor> obtenerTodosLosContenedores(){
        return contenedorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Contenedor> obtenerContenedorPorId(@NonNull  Integer id){
        return contenedorRepository.findById(id);
    }

    @Transactional
    public Contenedor guardarContenedor(@NonNull Contenedor contenedor) {
        return contenedorRepository.save(contenedor);
    }

    @Transactional
    public void eliminarContenedor(@NonNull Integer id) {
        contenedorRepository.deleteById(id);
    }
}
