package com.smart_waste.utn.controllers;

import com.smart_waste.utn.services.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/contactar-operador")
    public ResponseEntity<?> contactarOperador(@RequestBody Map<String, String> payload) {
        String destino = payload.get("destino");
        String asunto = payload.get("asunto");
        String mensaje = payload.get("mensaje");
        String nombreAdmin = payload.get("nombreAdmin");
        String correoAdmin = payload.get("correoAdmin");

        if (destino == null || destino.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El correo del operador es requerido."));
        }

        emailService.enviarCorreoAOperador(destino, asunto, mensaje, nombreAdmin, correoAdmin);
        return ResponseEntity.ok(Map.of("mensaje", "Correo enviado exitosamente"));
    }
}