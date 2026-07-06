package com.smart_waste.utn.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_waste.utn.models.TokenRevocado;

public interface TokenRevocadoRepository extends JpaRepository<TokenRevocado, Long>{

    Optional<TokenRevocado> findByTokenJwt(String tokenJwt);
}
