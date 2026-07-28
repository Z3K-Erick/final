package com.horizon.backend.services;

import com.horizon.backend.models.Guia;
import com.horizon.backend.repositories.GuiaRepository;
import org.springframework.stereotype.Service;

@Service
public class GuiaService {
    private final GuiaRepository guiaRepository;

    public GuiaService(GuiaRepository guiaRepository) {
        this.guiaRepository = guiaRepository;
    }

    // Obtiene un guía por su ID.
    public Guia obtenerPorId(Long id) {
        return guiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Guía no encontrado con ID: " + id));
    }

    // Obtiene el perfil de guía asociado a un usuario (para resolver idGuia tras el login).
    public Guia obtenerPorIdUsuario(Long idUsuario) {
        Guia guia = guiaRepository.findByIdUsuario(idUsuario);
        if (guia == null) {
            throw new IllegalArgumentException("No existe un perfil de Guía para el usuario con ID: " + idUsuario);
        }
        return guia;
    }

    // Actualiza los datos de perfil del guía (especialidad, experiencia, descripción, foto).
    // Usado por el asistente de registro de guía para completar el perfil creado en AuthService.registrar.
    public Guia actualizar(Long id, Guia datos) {
        return guiaRepository.findById(id)
                .map(guia -> {
                    guia.setEspecialidad(datos.getEspecialidad());
                    guia.setExperienciaAnios(datos.getExperienciaAnios());
                    guia.setDescripcion(datos.getDescripcion());
                    guia.setFotoUrl(datos.getFotoUrl());
                    return guiaRepository.save(guia);
                })
                .orElseThrow(() -> new IllegalArgumentException("Guía no encontrado con ID: " + id));
    }
}