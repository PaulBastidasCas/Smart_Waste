package com.smart_waste.utn.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_waste.utn.models.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer>{

    Optional<Rol> findByRolNombre(String nombre);
}
