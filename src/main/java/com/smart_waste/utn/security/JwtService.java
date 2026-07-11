package com.smart_waste.utn.security;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    private final SecretKey key;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generarToken(UserDetails userDetails) {
        UsuarioPersonalizadoDetalles usuarioDetalles = (UsuarioPersonalizadoDetalles) userDetails;
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("rol", usuarioDetalles.getUsuario().getUsuRol().getRolNombre());
        extraClaims.put("id", usuarioDetalles.getUsuario().getUsuId().toString());

        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(key) 
                .compact();
    }

    public String extraerCorreo(String token) {
        return extraerClaim(token, claims -> claims.getSubject());
    }

    public String extraerJti(String token) {
        return extraerClaim(token, claims -> claims.getId());
    }

    public Date extraerExpiracion(String token) {
        return extraerClaim(token, claims -> claims.getExpiration());
    }

    public <T> T extraerClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
                
        return claimsResolver.apply(claims);
    }

    public boolean validarToken(String token, UserDetails userDetails) {
        final String correo = extraerCorreo(token);
        return (correo.equals(userDetails.getUsername()) && !isTokenExpirado(token));
    }

    private boolean isTokenExpirado(String token) {
        return extraerExpiracion(token).before(new Date());
    }
}
