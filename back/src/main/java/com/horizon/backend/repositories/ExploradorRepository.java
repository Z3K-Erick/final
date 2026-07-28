package com.horizon.backend.repositories;

import com.horizon.backend.models.Explorador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExploradorRepository extends JpaRepository<Explorador, Long> {
    Explorador findByIdUsuario(Long idUsuario);
}