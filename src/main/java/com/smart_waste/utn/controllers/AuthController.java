package com.smart_waste.utn.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart_waste.utn.models.Usuario;
import com.smart_waste.utn.security.JwtService;
import com.smart_waste.utn.services.UsuarioPersonalizadoDetallesService;
import com.smart_waste.utn.services.UsuarioService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final UsuarioPersonalizadoDetallesService userDetailsService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, 
                          UsuarioService usuarioService,
                          UsuarioPersonalizadoDetallesService userDetailsService, 
                          JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registrar(@RequestBody Usuario request) {
        Usuario usuarioGuardado = usuarioService.registrarUsuario(request);
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(usuarioGuardado.getCorreo());
        
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.correo(), request.password())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.correo());
        String token = jwtService.generateToken(userDetails);
        
        return ResponseEntity.ok(new AuthResponse(token));
    }


    public record LoginRequest(String correo, String password) {}
    public record AuthResponse(String token) {}
}
