package com.giordano.carros.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giordano.carros.domain.entity.Carro;

import java.util.List;

public interface CarroRepository extends JpaRepository<Carro, Long> {
    List<Carro> findByTipo(String tipo);
}
