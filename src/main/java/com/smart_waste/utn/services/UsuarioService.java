package com.smart_waste.utn.services;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;

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

   @Transactional(readOnly = true)
    public Usuario obtenerPorCorreo(@NonNull String correo){
        return usuarioRepository.findByUsuCorreo(correo)
        .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con correo: " + correo));
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario cambiarRolUsuario(@NonNull UUID id, String nuevoRolNombre) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            
        if (usuario.getUsuRol() != null) {
            usuario.getUsuRol().setRolNombre(nuevoRolNombre);
        }
        
        return usuarioRepository.save(usuario);
    }

    @SuppressWarnings("null")
    @Transactional
    public void eliminarUsuario(@NonNull UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        usuarioRepository.delete(usuario); 
    }

    @Transactional(readOnly = true)
    public String resolverCorreoUsuario(Principal principal, String correoParam) {
        String email = null;
        if (principal != null) {
            if (principal instanceof Authentication auth) {
                Object p = auth.getPrincipal();
                if (p instanceof UserDetails userDetails) {
                    email = userDetails.getUsername();
                }
            }
            if (email == null) {
                email = principal.getName();
            }
        }
        if (email == null || email.isEmpty()) {
            email = correoParam;
        }
        return (email == null || email.isEmpty()) ? "estudiante@utn.edu.ec" : email;
    }
}