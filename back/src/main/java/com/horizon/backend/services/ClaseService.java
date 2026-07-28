package com.horizon.backend.services;

import com.horizon.backend.models.Clase;
import com.horizon.backend.repositories.ClaseRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClaseService {

    private final ClaseRepository claseRepository;

    public ClaseService(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }

    // Obtiene una lista de todas las clases.
    public List<Clase> obtenerTodas() {
        return claseRepository.findAll();
    }

    // Crea una nueva clase en la base de datos.
    public Clase crearClase(Clase clase) {
        // Valida que la fecha de la clase no sea en el pasado.
        if (clase.getFecha() != null && clase.getFecha().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de la clase no puede estar en el pasado.");
        }
        return claseRepository.save(clase);
    }
}