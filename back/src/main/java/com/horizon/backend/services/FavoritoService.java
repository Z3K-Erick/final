package com.horizon.backend.services;

import com.horizon.backend.models.Favorito;
import com.horizon.backend.repositories.EventoRepository;
import com.horizon.backend.repositories.FavoritoRepository;
import com.horizon.backend.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public FavoritoService(FavoritoRepository favoritoRepository,
                           UsuarioRepository usuarioRepository,
                           EventoRepository eventoRepository) {
        this.favoritoRepository = favoritoRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    // Actividades guardadas por un usuario.
    public List<Favorito> obtenerPorUsuario(Long idUsuario) {
        return favoritoRepository.findByIdUsuario(idUsuario);
    }

    public boolean estaGuardado(Long idUsuario, Long idEvento) {
        return favoritoRepository.existsByIdUsuarioAndIdEvento(idUsuario, idEvento);
    }

    /**
     * Guarda o quita la actividad según su estado actual, y devuelve
     * si quedó guardada. Un solo endpoint evita que la interfaz tenga
     * que consultar primero y decidir después: el estado del botón
     * viene siempre de la respuesta del servidor.
     */
    public boolean alternar(Long idUsuario, Long idEvento) {
        if (idUsuario == null || idEvento == null) {
            throw new IllegalArgumentException("Se requieren idUsuario e idEvento.");
        }

        if (!usuarioRepository.existsById(idUsuario)) {
            throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no existe.");
        }

        if (!eventoRepository.existsById(idEvento)) {
            throw new IllegalArgumentException("El evento con ID " + idEvento + " no existe.");
        }

        return favoritoRepository.findByIdUsuarioAndIdEvento(idUsuario, idEvento)
                .map(favorito -> {
                    favoritoRepository.delete(favorito);
                    return false; // se quitó
                })
                .orElseGet(() -> {
                    Favorito nuevo = new Favorito();
                    nuevo.setIdUsuario(idUsuario);
                    nuevo.setIdEvento(idEvento);
                    nuevo.setFecha(LocalDateTime.now());
                    favoritoRepository.save(nuevo);
                    return true; // se guardó
                });
    }
}
