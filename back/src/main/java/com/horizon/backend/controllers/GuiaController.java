package com.horizon.backend.controllers;

import com.horizon.backend.models.Guia;
import com.horizon.backend.services.GuiaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guias")
// link http://localhost:8080/api/guias
public class GuiaController {
    private final GuiaService guiaService;

    public GuiaController(GuiaService guiaService) {
        this.guiaService = guiaService;
    }

    @GetMapping("/{id}")
    // Endpoint para obtener el perfil de un guía.
    public Guia obtenerPerfil(@PathVariable Long id) {
        return guiaService.obtenerPorId(id);
    }

    @GetMapping("/usuario/{idUsuario}")
    // Endpoint para obtener el perfil de guía a partir del ID de usuario.
    public Guia obtenerPorUsuario(@PathVariable Long idUsuario) {
        return guiaService.obtenerPorIdUsuario(idUsuario);
    }

    @PutMapping("/{id}")
    // Endpoint para completar/actualizar el perfil de guía (usado por el registro de guía).
    public Guia actualizarPerfil(@PathVariable Long id, @RequestBody Guia guia) {
        return guiaService.actualizar(id, guia);
    }
}