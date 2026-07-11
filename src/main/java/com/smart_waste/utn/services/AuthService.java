package com.smart_waste.utn.services;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.Rol;
import com.smart_waste.utn.models.Usuario;
import com.smart_waste.utn.models.dto.AuthResponse;
import com.smart_waste.utn.models.dto.LoginRequest;
import com.smart_waste.utn.models.dto.RegistroRequest;
import com.smart_waste.utn.repositories.FacultadRepository;
import com.smart_waste.utn.repositories.RolRepository;
import com.smart_waste.utn.repositories.TipoIdentificacionRepository;
import com.smart_waste.utn.repositories.UsuarioRepository;
import com.smart_waste.utn.security.JwtService;
import com.smart_waste.utn.security.UsuarioPersonalizadoDetalles;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final FacultadRepository facultadRepository;
    private final TipoIdentificacionRepository tipoIdentificacionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioRepository usuarioRepository, RolRepository rolRepository,
                       FacultadRepository facultadRepository, TipoIdentificacionRepository tipoIdentificacionRepository,
                       PasswordEncoder passwordEncoder, JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.facultadRepository = facultadRepository;
        this.tipoIdentificacionRepository = tipoIdentificacionRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @SuppressWarnings("null")
    @Transactional
    public AuthResponse registrar(RegistroRequest request) {
        if (usuarioRepository.findByUsuCorreo(request.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setUsuNombre(request.getNombre());
        usuario.setUsuApellido(request.getApellido());
        usuario.setUsuCorreo(request.getCorreo());
        usuario.setUsuPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setUsuActivo(true);

        Rol rolDefault = rolRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Error interno: Rol por defecto no encontrado en la base de datos"));
        usuario.setUsuRol(rolDefault);

        if (request.getIdentificacion() != null) {
            usuario.setUsuIdentificacion(request.getIdentificacion());
        }
        if (request.getTipoIdentificacionId() != null) {
            usuario.setUsuTipoIdentificacion(tipoIdentificacionRepository.findById(request.getTipoIdentificacionId()).orElse(null));
        }
        if (request.getFacultadId() != null) {
            usuario.setUsuFacultad(facultadRepository.findById(request.getFacultadId()).orElse(null));
        }

        usuarioRepository.save(usuario);

        String token = jwtService.generarToken(new UsuarioPersonalizadoDetalles(usuario));
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByUsuCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                
        usuario.setUsuUltimoLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);

        String token = jwtService.generarToken(new UsuarioPersonalizadoDetalles(usuario));
        return new AuthResponse(token);
    }
}
