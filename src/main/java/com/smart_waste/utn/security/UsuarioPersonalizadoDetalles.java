package com.smart_waste.utn.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smart_waste.utn.models.Usuario;

public class UsuarioPersonalizadoDetalles implements UserDetails{

    private final Usuario usuario;

    public UsuarioPersonalizadoDetalles(Usuario usuario){
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getRolNombre()));
    }

    @Override
    public String getPassword() {
       return usuario.getUsuPasswordHash();
    }

    @Override
    public String getUsername() {
        return usuario.getUsuCorreo();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() {
        return usuario.getUsuActivo();
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
