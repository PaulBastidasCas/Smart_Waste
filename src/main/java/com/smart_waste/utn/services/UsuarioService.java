package com.smart_waste.utn.services;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.Usuario;
import com.smart_waste.utn.repositories.UsuarioRepository;
import com.smart_waste.utn.exceptions.ResourceNotFoundException;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public Usuario obtenerPorId(@NonNull UUID id){
        return usuarioRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarPorFacultad(Integer facultadId) {
        return usuarioRepository.findByUsuFacultad_FacId(facultadId);
    }

    @Transactional
    public Usuario actualizarFotoPerfil(@NonNull UUID id, String fotoBase64) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            
        usuario.setUsuFotoPerfilBase64(fotoBase64);
        return usuarioRepository.save(usuario);
    }
}
