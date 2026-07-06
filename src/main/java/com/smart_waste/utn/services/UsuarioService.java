package com.smart_waste.utn.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.TokenRevocado;
import com.smart_waste.utn.models.Usuario;
import com.smart_waste.utn.repositories.TokenRevocadoRepository;
import com.smart_waste.utn.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TokenRevocadoRepository tokenRevocadoRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          TokenRevocadoRepository tokenRevocadoRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.tokenRevocadoRepository = tokenRevocadoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        Objects.requireNonNull(usuario, "El usuario no puede ser nulo");

        String hash = passwordEncoder.encode(usuario.getPasswordHash());
        usuario.setPasswordHash(hash);
        
        if (usuario.getActivo() == null) {
            usuario.setActivo(true);
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public TokenRevocado revocarToken(TokenRevocado tokenRevocado) {
        return tokenRevocadoRepository.save(Objects.requireNonNull(tokenRevocado, "El token no puede ser nulo"));
    }

    @Transactional(readOnly = true)
    public boolean isTokenRevocado(String token) {
        return tokenRevocadoRepository.findByTokenJwt(token).isPresent();
    }
}
