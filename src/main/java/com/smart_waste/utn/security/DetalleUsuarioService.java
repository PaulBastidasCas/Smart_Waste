package com.smart_waste.utn.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.Usuario;
import com.smart_waste.utn.repositories.UsuarioRepository;

@Service
public class DetalleUsuarioService implements UserDetailsService{

    private final UsuarioRepository usuarioRepository;

    public DetalleUsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Usuario usuario = usuarioRepository.findByUsuCorreo(username)
       .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
       return new UsuarioPersonalizadoDetalles(usuario);
    }

}
