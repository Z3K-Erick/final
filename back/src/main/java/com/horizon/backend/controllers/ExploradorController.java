package com.horizon.backend.controllers;

import com.horizon.backend.models.Explorador;
import com.horizon.backend.services.ExploradorService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/exploradores")
// link http://localhost:8080/api/exploradores
public class ExploradorController {

    private final ExploradorService exploradorService;

    public ExploradorController(ExploradorService exploradorService) {
        this.exploradorService = exploradorService;
    }

    @GetMapping("/{id}")
    // Endpoint para obtener el perfil de un explorador por su ID.
    public Explorador obtenerPerfil(@PathVariable Long id) {
        return exploradorService.obtenerPorId(id);
    }

    @GetMapping("/usuario/{idUsuario}")
    // Endpoint para obtener el perfil de explorador a partir del ID de usuario.
    public Explorador obtenerPorUsuario(@PathVariable Long idUsuario) {
        return exploradorService.obtenerPorIdUsuario(idUsuario);
    }

    // Actualiza el nivel de experiencia: PRINCIPIANTE, INTERMEDIO o AVANZADO.
    @PutMapping("/{id}/nivel")
    public Explorador actualizarNivel(@PathVariable Long id, @RequestBody Map<String, String> cuerpo) {
        return exploradorService.actualizarNivel(id, cuerpo.get("nivel"));
    }
}
