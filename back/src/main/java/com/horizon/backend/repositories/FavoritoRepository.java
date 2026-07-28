package com.horizon.backend.repositories;

import com.horizon.backend.models.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    // Todas las actividades guardadas por un usuario.
    List<Favorito> findByIdUsuario(Long idUsuario);

    // Para saber si ya está guardada y para poder quitarla.
    Optional<Favorito> findByIdUsuarioAndIdEvento(Long idUsuario, Long idEvento);

    boolean existsByIdUsuarioAndIdEvento(Long idUsuario, Long idEvento);
}
