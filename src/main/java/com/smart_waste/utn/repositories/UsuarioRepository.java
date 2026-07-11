package com.smart_waste.utn.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_waste.utn.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByUsuCorreo(String correo);

    List<Usuario> findByUsuFacultad_FacId(Integer facId);
}
