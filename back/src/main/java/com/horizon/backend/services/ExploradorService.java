package com.horizon.backend.services;

import com.horizon.backend.models.Explorador;
import com.horizon.backend.repositories.ExploradorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExploradorService {

    // Valores permitidos en la columna `nivel` según el esquema.
    private static final List<String> NIVELES = List.of("PRINCIPIANTE", "INTERMEDIO", "AVANZADO");

    private final ExploradorRepository exploradorRepository;

    public ExploradorService(ExploradorRepository exploradorRepository) {
        this.exploradorRepository = exploradorRepository;
    }

    // Obtiene un explorador por su ID.
    public Explorador obtenerPorId(Long id) {
        return exploradorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Explorador no encontrado con ID: " + id));
    }

    // Obtiene el perfil de explorador asociado a un usuario (para resolver idExplorador tras el login).
    public Explorador obtenerPorIdUsuario(Long idUsuario) {
        Explorador explorador = exploradorRepository.findByIdUsuario(idUsuario);
        if (explorador == null) {
            throw new IllegalArgumentException("No existe un perfil de Explorador para el usuario con ID: " + idUsuario);
        }
        return explorador;
    }

    // Actualiza el nivel de experiencia declarado por el explorador.
    public Explorador actualizarNivel(Long idExplorador, String nivel) {
        if (nivel == null) {
            throw new IllegalArgumentException("Debes indicar un nivel.");
        }

        String valor = nivel.toUpperCase();
        if (!NIVELES.contains(valor)) {
            throw new IllegalArgumentException("Nivel no válido: " + nivel + ". Usa PRINCIPIANTE, INTERMEDIO o AVANZADO.");
        }

        Explorador explorador = obtenerPorId(idExplorador);
        explorador.setNivel(valor);
        return exploradorRepository.save(explorador);
    }
}
