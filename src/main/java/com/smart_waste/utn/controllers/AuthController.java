package com.smart_waste.utn.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smart_waste.utn.models.dto.AuthResponse;
import com.smart_waste.utn.models.dto.LoginRequest;
import com.smart_waste.utn.models.dto.RegistroRequest;
import com.smart_waste.utn.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registrar(@Valid @RequestBody RegistroRequest request) {
        return ResponseEntity.ok(authService.registrar(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/olvide-password")
    public ResponseEntity<String> solicitarRecuperacion(@RequestParam String correo) {
        authService.solicitarRecuperacion(correo);
        return ResponseEntity.ok("Si el correo existe, se ha enviado un enlace de recuperación.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> restablecerPassword(@RequestParam String token, @RequestParam String nuevaClave) {
        authService.restablecerPassword(token, nuevaClave);
        return ResponseEntity.ok("Contraseña actualizada con éxito.");
    }
}
