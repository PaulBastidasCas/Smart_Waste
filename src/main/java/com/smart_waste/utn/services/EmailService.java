package com.smart_waste.utn.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async("emailExecutor")
    public void enviarCorreoBienvenida(String destino, String nombre) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destino);
        mensaje.setSubject("¡Bienvenido a UTN Smart Waste!");
        mensaje.setText("Hola " + nombre + ",\n\nFelicidades por unirte a nuestra iniciativa para mejorar la gestión ecológica del Campus El Olivo. ¡Juntos hacemos la diferencia!\n\nSaludos,\nEquipo UTN Smart Waste");
        mailSender.send(mensaje);
    }

    @Async("emailExecutor")
    public void enviarCorreoRecuperacion(String destino, String token) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destino);
        mensaje.setSubject("Recuperación de Contraseña - UTN Smart Waste");
        
        String urlRecuperacion = frontendUrl + "/reset-password?token=" + token;
        
        mensaje.setText("Has solicitado restablecer tu contraseña. Haz clic en el siguiente enlace:\n\n" 
                + urlRecuperacion + "\n\nSi no fuiste tú, ignora este mensaje.");
        mailSender.send(mensaje);
    }

    @Async("emailExecutor")
    public void enviarCorreoAOperador(String destino, String asunto, String mensajeCuerpo, String nombreAdmin, String correoAdmin) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destino);
        mensaje.setReplyTo(correoAdmin);
        mensaje.setSubject("Smart Waste UTN - " + asunto);
        
        String cuerpo = "Hola,\n\n"
                + "Has recibido un mensaje del administrador " + nombreAdmin + " (" + correoAdmin + ") "
                + "respecto a una recolección reciente en el campus:\n\n"
                + "\"" + mensajeCuerpo + "\"\n\n"
                + "Por favor, comunícate directamente respondiendo a este correo.\n\n"
                + "Saludos,\nSistema UTN Smart Waste";
                
        mensaje.setText(cuerpo);
        mailSender.send(mensaje);
    }
}
