package com.smart_waste.utn.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.exceptions.ResourceNotFoundException;
import com.smart_waste.utn.models.Rol;
import com.smart_waste.utn.models.TokenRevocado;
import com.smart_waste.utn.models.Usuario;
import com.smart_waste.utn.models.dto.AuthResponse;
import com.smart_waste.utn.models.dto.LoginRequest;
import com.smart_waste.utn.models.dto.RegistroRequest;
import com.smart_waste.utn.repositories.FacultadRepository;
import com.smart_waste.utn.repositories.RolRepository;
import com.smart_waste.utn.repositories.TipoIdentificacionRepository;
import com.smart_waste.utn.repositories.TokenRevocadoRepository;
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
    private final EmailService emailService;
    private final TokenRevocadoRepository tokenRevocadoRepository;

    public AuthService(UsuarioRepository usuarioRepository, RolRepository rolRepository,
                       FacultadRepository facultadRepository, TipoIdentificacionRepository tipoIdentificacionRepository,
                       PasswordEncoder passwordEncoder, JwtService jwtService,
                       AuthenticationManager authenticationManager, EmailService emailService,
                       TokenRevocadoRepository tokenRevocadoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.facultadRepository = facultadRepository;
        this.tipoIdentificacionRepository = tipoIdentificacionRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
        this.tokenRevocadoRepository = tokenRevocadoRepository;
    }

    @SuppressWarnings("null")
    @Transactional
    public AuthResponse registrar(RegistroRequest request) {
        if (usuarioRepository.findByUsuCorreo(request.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setUsuNombre(request.getNombre());
        usuario.setUsuApellido(request.getApellido());
        usuario.setUsuCorreo(request.getCorreo());
        usuario.setUsuPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setUsuActivo(true);

        Rol rolDefault = rolRepository.findByRolNombre("ESTUDIANTE")
                .orElseThrow(() -> new ResourceNotFoundException("Rol ESTUDIANTE no encontrado en el sistema"));
        
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

        emailService.enviarCorreoBienvenida(usuario.getUsuCorreo(), usuario.getUsuNombre());

        usuarioRepository.save(usuario);

        String token = jwtService.generarToken(new UsuarioPersonalizadoDetalles(usuario));
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByUsuCorreo(request.getCorreo())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
                
        usuario.setUsuUltimoLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);

        String token = jwtService.generarToken(new UsuarioPersonalizadoDetalles(usuario));
        return new AuthResponse(token);
    }

    public void solicitarRecuperacion(String correo) {
        Usuario usuario = usuarioRepository.findByUsuCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        String token = jwtService.generarToken(new UsuarioPersonalizadoDetalles(usuario));
        emailService.enviarCorreoRecuperacion(correo, token);
    }

    public void restablecerPassword(String token, String nuevaClave) {
        String correo = jwtService.extraerCorreo(token); 
        
        Usuario usuario = usuarioRepository.findByUsuCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        usuario.setUsuPasswordHash(passwordEncoder.encode(nuevaClave));
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void logout(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String jti = jwtService.extraerJti(jwt);
            Date expiracion = jwtService.extraerExpiracion(jwt);
            String correo = jwtService.extraerCorreo(jwt); 

            if (!tokenRevocadoRepository.existsByTokJti(jti)) {
                Usuario usuario = usuarioRepository.findByUsuCorreo(correo)
                        .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

                TokenRevocado tokenRevocado = new TokenRevocado();
                tokenRevocado.setTokJti(jti);
                tokenRevocado.setTokUsuario(usuario); 
                
                tokenRevocado.setTokExpira(
                    expiracion.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                );
                
                tokenRevocadoRepository.save(tokenRevocado);
            }
        }
    }
}