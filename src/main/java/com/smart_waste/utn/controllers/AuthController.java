package com.smart_waste.utn.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.smart_waste.utn.models.dto.AuthResponse;
import com.smart_waste.utn.models.dto.LoginRequest;
import com.smart_waste.utn.models.dto.RegistroRequest;
import com.smart_waste.utn.services.AuthService;
import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
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

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        authService.logout(authHeader);
        return ResponseEntity.ok(Map.of("mensaje", "Sesión cerrada correctamente. Token revocado."));
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