package com.horizon.backend.services;

import com.horizon.backend.models.Evento;
import com.horizon.backend.repositories.EventoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    // Obtiene una lista de todos los eventos.
    public List<Evento> obtenerTodos() {
        return eventoRepository.findAll();
    }

    // Obtiene un evento por su ID. Devuelve null si no existe;
    // el controlador se encarga de traducirlo a un 404.
    public Evento obtenerPorId(Long id) {
        return eventoRepository.findById(id).orElse(null);
    }

    // Crea un nuevo evento en la base de datos.
    public Evento crearEvento(Evento evento) {
        return eventoRepository.save(evento);
    }
}
