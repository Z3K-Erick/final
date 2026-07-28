package com.horizon.backend.repositories;

import com.horizon.backend.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Eventos programados en una zona. Se usa antes de borrarla:
    // la llave foránea impide eliminar una zona que todavía los tiene.
    List<Evento> findByIdZona(Long idZona);

    long countByIdZona(Long idZona);
}
