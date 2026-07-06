package com.smart_waste.utn.services;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_waste.utn.models.Insignia;
import com.smart_waste.utn.models.RankingMensual;
import com.smart_waste.utn.models.RetoComunitario;
import com.smart_waste.utn.models.UsuarioInsignia;
import com.smart_waste.utn.repositories.InsigniaRepository;
import com.smart_waste.utn.repositories.RankingMensualRepository;
import com.smart_waste.utn.repositories.RetoComunitarioRepository;
import com.smart_waste.utn.repositories.UsuarioInsigniaRepository;

@Service
public class GamificacionService {
    private final InsigniaRepository insigniaRepository;
    private final RetoComunitarioRepository retoComunitarioRepository;
    private final RankingMensualRepository rankingMensualRepository;
    private final UsuarioInsigniaRepository usuarioInsigniaRepository;

    public GamificacionService(InsigniaRepository insigniaRepository,
                               RetoComunitarioRepository retoComunitarioRepository,
                               RankingMensualRepository rankingMensualRepository,
                               UsuarioInsigniaRepository usuarioInsigniaRepository) {
        this.insigniaRepository = insigniaRepository;
        this.retoComunitarioRepository = retoComunitarioRepository;
        this.rankingMensualRepository = rankingMensualRepository;
        this.usuarioInsigniaRepository = usuarioInsigniaRepository;
    }

    @Transactional(readOnly = true)
    public List<Insignia> obtenerTodasLasInsignias() {
        return insigniaRepository.findAll();
    }

    @Transactional
    public Insignia guardarInsignia(Insignia insignia) {
        return insigniaRepository.save(Objects.requireNonNull(insignia, "La insignia no puede ser nula"));
    }

    @Transactional(readOnly = true)
    public List<RetoComunitario> obtenerTodosLosRetos() {
        return retoComunitarioRepository.findAll();
    }

    @Transactional
    public RetoComunitario guardarReto(RetoComunitario reto) {
        return retoComunitarioRepository.save(Objects.requireNonNull(reto, "El reto no puede ser nulo"));
    }

    @Transactional(readOnly = true)
    public List<RankingMensual> obtenerRanking() {
        return rankingMensualRepository.findAll();
    }

    @Transactional
    public RankingMensual guardarRanking(RankingMensual ranking) {
        return rankingMensualRepository.save(Objects.requireNonNull(ranking, "El registro de ranking no puede ser nulo"));
    }

    @Transactional(readOnly = true)
    public List<UsuarioInsignia> obtenerInsigniasOtorgadas() {
        return usuarioInsigniaRepository.findAll();
    }

    @Transactional
    public UsuarioInsignia otorgarInsigniaAUsuario(UsuarioInsignia usuarioInsignia) {
        return usuarioInsigniaRepository.save(Objects.requireNonNull(usuarioInsignia, "La asignación no puede ser nula"));
    }
}
