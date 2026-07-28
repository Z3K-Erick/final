package com.horizon.backend.services;

import com.horizon.backend.models.Deporte;
import com.horizon.backend.repositories.DeporteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeporteService {

    private final DeporteRepository deporteRepository;

    public DeporteService(DeporteRepository deporteRepository) {
        this.deporteRepository = deporteRepository;
    }

    // Obtiene una lista de todos los deportes.
    public List<Deporte> obtenerTodos() {
        return deporteRepository.findAll();
    }
}