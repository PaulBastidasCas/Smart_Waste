package com.smart_waste.utn.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smart_waste.utn.repositories.TokenRevocadoRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final DetalleUsuarioService detalleUsuarioService;
    private final TokenRevocadoRepository tokenRevocadoRepository;

    public JwtAuthenticationFilter(JwtService jwtService, 
                                   DetalleUsuarioService detalleUsuarioService, 
                                   TokenRevocadoRepository tokenRevocadoRepository) {
        this.jwtService = jwtService;
        this.detalleUsuarioService = detalleUsuarioService;
        this.tokenRevocadoRepository = tokenRevocadoRepository;
    }
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.contains("/api/auth") || path.contains("/api/catalogos")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String correoUsuario;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("No se recibió Token en la ruta protegida: {}", path);
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        
        try {
            correoUsuario = jwtService.extraerCorreo(jwt);
            String jti = jwtService.extraerJti(jwt);

            if (tokenRevocadoRepository.existsByTokJti(jti)) {
                log.warn("Intento de acceso con token revocado (JTI: {})", jti);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token revocado");
                return;
            }

            if (correoUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.detalleUsuarioService.loadUserByUsername(correoUsuario);

                if (jwtService.validarToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("Token validado para usuario: {}", correoUsuario);
                } else {
                    log.warn("Token inválido para usuario: {}", correoUsuario);
                }
            }
        } catch (Exception e) {
            log.error("Error crítico validando el token: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT inválido o expirado");
            return;
        }
        
        filterChain.doFilter(request, response);
    }
}