package com.horizon.backend.repositories;

import com.horizon.backend.models.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
    // idReserva es UNIQUE en la BD: como máximo una reseña por reserva.
    boolean existsByIdReserva(Long idReserva);
}