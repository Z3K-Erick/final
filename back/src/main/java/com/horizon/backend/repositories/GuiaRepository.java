package com.horizon.backend.repositories;

import com.horizon.backend.models.Guia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuiaRepository extends JpaRepository<Guia, Long> {
    Guia findByIdUsuario(Long idUsuario);
}