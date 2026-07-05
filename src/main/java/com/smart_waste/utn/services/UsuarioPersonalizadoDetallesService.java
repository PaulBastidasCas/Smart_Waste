package com.smart_waste.utn.services;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smart_waste.utn.models.Usuario;
import com.smart_waste.utn.repositories.UsuarioRepository;

@Service
public class UsuarioPersonalizadoDetallesService implements UserDetailsService{
    
    private final UsuarioRepository usuarioRepository;

    public UsuarioPersonalizadoDetallesService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + username));

        if (!usuario.getActivo()) {
            throw new RuntimeException("El usuario está desactivado");
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name());

        return new org.springframework.security.core.userdetails.User(
                usuario.getCorreo(), 
                usuario.getPasswordHash(), 
                Collections.singletonList(authority)
        );
    }
}
