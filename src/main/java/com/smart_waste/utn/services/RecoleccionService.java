package com.smart_waste.utn.services;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.exceptions.ResourceNotFoundException;
import com.smart_waste.utn.models.Contenedor;
import com.smart_waste.utn.models.RegistroRecoleccion;
import com.smart_waste.utn.models.Usuario;
import com.smart_waste.utn.models.enums.EstadoLlenado;
import com.smart_waste.utn.repositories.ContenedorRepository;
import com.smart_waste.utn.repositories.RegistroRecoleccionRepository;
import com.smart_waste.utn.repositories.UsuarioRepository;

@Service
public class RecoleccionService {

    private final RegistroRecoleccionRepository registroRecoleccionRepository;
    private final ContenedorRepository contenedorRepository;
    private final UsuarioRepository usuarioRepository;
    private final GamificacionService gamificacionService;

    @Value("${app.contenedor.umbral.critico}")
    private int umbralCritico;

    @Value("${app.contenedor.umbral.medio}")
    private int umbralMedio;

    public RecoleccionService(RegistroRecoleccionRepository registroRecoleccionRepository,
            ContenedorRepository contenedorRepository,
            UsuarioRepository usuarioRepository,
            GamificacionService gamificacionService) {
        this.registroRecoleccionRepository = registroRecoleccionRepository;
        this.contenedorRepository = contenedorRepository;
        this.usuarioRepository = usuarioRepository;
        this.gamificacionService = gamificacionService;
    }

    @Transactional
    public RegistroRecoleccion registrarVaciado(
            @NonNull Integer contenedorId,
            Double cantidadRecolectada,
            String observaciones,
            String fotoBase64,
            Boolean tieneClasificacionErronea,
            String descripcionError,
            String correoEncargado) {

        Usuario encargado = usuarioRepository.findByUsuCorreo(correoEncargado)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Usuario encargado no encontrado en la base de datos"));

        Contenedor contenedor = contenedorRepository.findById(contenedorId)
                .orElseThrow(() -> new ResourceNotFoundException("Contenedor no encontrado"));

        double capacidad = contenedor.getConCapacidadLitros().doubleValue();
        int porcentajeActual = contenedor.getConNivelLlenadoPct();

        double volumenActual = (capacidad * porcentajeActual) / 100.0;
        double nuevoVolumen = Math.max(0, volumenActual - cantidadRecolectada);
        int nuevoPorcentaje = (int) Math.round((nuevoVolumen / capacidad) * 100.0);

        EstadoLlenado nuevoEstado = EstadoLlenado.VACIO;
        if (nuevoPorcentaje >= umbralCritico)
            nuevoEstado = EstadoLlenado.CRITICO;
        else if (nuevoPorcentaje >= umbralMedio)
            nuevoEstado = EstadoLlenado.MEDIO;

        contenedor.setConNivelLlenadoPct(nuevoPorcentaje);
        contenedor.setConEstadoLlenado(nuevoEstado);
        contenedorRepository.save(contenedor);

        RegistroRecoleccion registro = new RegistroRecoleccion();
        registro.setRegContenedor(contenedor);
        registro.setRegEncargado(encargado);

        registro.setRegNivelAntesPct(porcentajeActual);
        registro.setRegNivelDespuesPct(nuevoPorcentaje);
        registro.setRegEstadoLLenado(nuevoEstado);

        registro.setRegPesoEstimadoKg(BigDecimal.valueOf(cantidadRecolectada));
        registro.setRegObservaciones(observaciones);
        registro.setRegFotoBase64(fotoBase64);

        boolean tieneError = tieneClasificacionErronea != null ? tieneClasificacionErronea : false;
        registro.setRegTieneClasificacionErronea(tieneError);
        registro.setRegDescripcionError(descripcionError);

        RegistroRecoleccion registroGuardado = registroRecoleccionRepository.save(registro);

        if (contenedor.getConFacultad() != null) {
            gamificacionService.actualizarProgresoRetosClasificacion(
                    contenedor.getConFacultad().getFacId(),
                    BigDecimal.valueOf(cantidadRecolectada),
                    !tieneError);
        }

        return registroGuardado;
    }

    @Transactional(readOnly = true)
    public List<RegistroRecoleccion> listarPorContenedor(Integer contenedorId) {
        return registroRecoleccionRepository.findByRegContenedor_ConIdOrderByRegFechaHoraRegistroDesc(contenedorId);
    }

    @Transactional(readOnly = true)
    public java.util.Map<String, Object> obtenerEstadisticasFacultad(Integer facultadId) {
        List<RegistroRecoleccion> recolecciones = registroRecoleccionRepository
                .findByRegContenedor_ConFacultad_FacId(facultadId);

        double totalKg = 0.0;
        for (RegistroRecoleccion r : recolecciones) {
            if (r.getRegPesoEstimadoKg() != null) {
                totalKg += r.getRegPesoEstimadoKg().doubleValue();
            }
        }

        List<java.util.Map<String, Object>> listRecent = recolecciones.stream()
                .sorted((r1, r2) -> r2.getRegFechaHoraRegistro().compareTo(r1.getRegFechaHoraRegistro()))
                .limit(10)
                .map(r -> {
                    java.util.Map<String, Object> map = new java.util.HashMap<>();
                    map.put("id", r.getRegId());
                    map.put("peso", r.getRegPesoEstimadoKg());
                    map.put("fecha", r.getRegFechaHoraRegistro());
                    map.put("contenedorCodigo", r.getRegContenedor().getConCodigo());
                    map.put("residuo", r.getRegContenedor().getConTipoResiduo().getTreNombre());
                    return map;
                })
                .collect(java.util.stream.Collectors.toList());

        java.util.Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("facultadId", facultadId);
        stats.put("totalKg", totalKg);
        stats.put("totalRecolecciones", recolecciones.size());
        stats.put("actividades", listRecent);

        return stats;
    }

    @Transactional(readOnly = true)
    public List<java.util.Map<String, Object>> obtenerRecientesGlobales() {
        List<RegistroRecoleccion> recolecciones = registroRecoleccionRepository
                .findTop3ByOrderByRegFechaHoraRegistroDesc();
        List<java.util.Map<String, Object>> result = new java.util.ArrayList<>();

        for (RegistroRecoleccion r : recolecciones) {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("id", r.getRegId());
            map.put("fecha", r.getRegFechaHoraRegistro().toString());
            map.put("peso", r.getRegPesoEstimadoKg());
            map.put("facultadNombre", r.getRegContenedor().getConFacultad().getFacNombre());

            double peso = r.getRegPesoEstimadoKg() != null ? r.getRegPesoEstimadoKg().doubleValue() : 0.0;
            double beneficio = Math.round((peso / 300.0) * 100.0 * 10.0) / 10.0; // 1 decimal place
            map.put("beneficio", beneficio);

            result.add(map);
        }
        return result;
    }

    @Transactional
    public RegistroRecoleccion procesarRegistroDesdeMap(Map<String, Object> payload, String correoEncargado) {
        Integer contenedorId = null;
        if (payload.get("contenedorId") != null) {
            contenedorId = ((Number) payload.get("contenedorId")).intValue();
        } else if (payload.get("regContenedorId") != null) {
            contenedorId = ((Number) payload.get("regContenedorId")).intValue();
        }

        Double pesoRecolectado = null;
        if (payload.get("pesoRecolectado") != null) {
            pesoRecolectado = ((Number) payload.get("pesoRecolectado")).doubleValue();
        } else if (payload.get("cantidadRecolectada") != null) {
            pesoRecolectado = ((Number) payload.get("cantidadRecolectada")).doubleValue();
        } else if (payload.get("regPesoEstimadoKg") != null) {
            pesoRecolectado = ((Number) payload.get("regPesoEstimadoKg")).doubleValue();
        }

        String observaciones = payload.get("observaciones") != null ? (String) payload.get("observaciones")
                : (String) payload.get("regObservaciones");

        String fotoBase64 = payload.get("regFotoBase64") != null ? (String) payload.get("regFotoBase64")
                : (String) payload.get("fotoBase64");

        Boolean tieneClasificacionErronea = (Boolean) payload.get("tieneClasificacionErronea");
        if (tieneClasificacionErronea == null) {
            Object val = payload.get("regTieneClasificacionErronea");
            if (val instanceof Boolean)
                tieneClasificacionErronea = (Boolean) val;
            else if (val instanceof String)
                tieneClasificacionErronea = Boolean.parseBoolean((String) val);
        }

        String descripcionError = payload.get("descripcionError") != null ? (String) payload.get("descripcionError")
                : (String) payload.get("regDescripcionError");

        if (contenedorId == null || pesoRecolectado == null) {
            throw new IllegalArgumentException("Los campos contenedorId y pesoRecolectado son obligatorios");
        }

        return registrarVaciado(contenedorId, pesoRecolectado, observaciones, fotoBase64,
                tieneClasificacionErronea, descripcionError, correoEncargado);
    }

    @Transactional(readOnly = true)
    public RegistroRecoleccion obtenerUltimaRecoleccionGlobalDetalle() {
        List<RegistroRecoleccion> recientes = registroRecoleccionRepository.findTop3ByOrderByRegFechaHoraRegistroDesc();
        return recientes.isEmpty() ? null : recientes.get(0);
    }
    
    @SuppressWarnings("null")
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerMetricasDashboard() {
        List<RegistroRecoleccion> todas = registroRecoleccionRepository.findAll();

        int totalRecolecciones = todas.size();

        double pesoTotalKg = todas.stream()
                .filter(r -> r.getRegPesoEstimadoKg() != null)
                .mapToDouble(r -> r.getRegPesoEstimadoKg().doubleValue())
                .sum();

        long alertasErrores = todas.stream()
                .filter(r -> Boolean.TRUE.equals(r.getRegTieneClasificacionErronea()))
                .count();

        int promedioLlenado = (int) Math.round(todas.stream()
                .filter(r -> r.getRegNivelAntesPct() != null)
                .mapToInt(RegistroRecoleccion::getRegNivelAntesPct)
                .average()
                .orElse(0.0));

        Map<String, Object> kpis = Map.of(
                "totalRecolecciones", totalRecolecciones,
                "pesoTotalKg", Math.round(pesoTotalKg * 10.0) / 10.0,
                "alertasErrores", alertasErrores,
                "promedioLlenado", promedioLlenado);

        Map<String, Long> conteoEstados = todas.stream()
                .filter(r -> r.getRegEstadoLLenado() != null)
                .collect(Collectors.groupingBy(r -> r.getRegEstadoLLenado().name(), Collectors.counting()));

        List<Map<String, Object>> datosEstado = conteoEstados.entrySet().stream()
            .map(e -> Map.<String, Object>of("name", e.getKey(), "value", e.getValue()))
            .collect(Collectors.toList());

        Map<String, Long> conteoFacultades = todas.stream()
                .filter(r -> r.getRegContenedor() != null && r.getRegContenedor().getConFacultad() != null)
                .collect(Collectors.groupingBy(r -> r.getRegContenedor().getConFacultad().getFacCodigo(),
                        Collectors.counting()));

        List<Map<String, Object>> datosFacultad = conteoFacultades.entrySet().stream()
            .map(e -> Map.<String, Object>of("facultad", e.getKey(), "recolecciones", e.getValue()))
            .collect(Collectors.toList());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        Map<String, Double> pesoPorDia = todas.stream()
                .filter(r -> r.getRegFechaHoraRegistro() != null && r.getRegPesoEstimadoKg() != null)
                .collect(Collectors.groupingBy(
                        r -> r.getRegFechaHoraRegistro().toLocalDate().format(formatter),
                        Collectors.summingDouble(r -> r.getRegPesoEstimadoKg().doubleValue())));

        List<Map<String, Object>> datosSemanales = pesoPorDia.entrySet().stream()
            .map(e -> Map.<String, Object>of("dia", e.getKey(), "peso", Math.round(e.getValue() * 10.0) / 10.0))
            .collect(Collectors.toList());

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("kpis", kpis);
        respuesta.put("datosEstado", datosEstado);
        respuesta.put("datosFacultad", datosFacultad);
        respuesta.put("datosSemanales", datosSemanales);

        return respuesta;
    }
}