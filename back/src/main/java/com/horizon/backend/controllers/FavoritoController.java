package com.horizon.backend.controllers;

import com.horizon.backend.models.Favorito;
import com.horizon.backend.services.FavoritoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    private final FavoritoService favoritoService;

    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    // Actividades guardadas por un usuario (pestaña "Guardados" del perfil).
    @GetMapping("/usuario/{idUsuario}")
    public List<Favorito> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return favoritoService.obtenerPorUsuario(idUsuario);
    }

    // Consulta si una actividad concreta ya está guardada.
    @GetMapping("/usuario/{idUsuario}/evento/{idEvento}")
    public Map<String, Boolean> estaGuardado(@PathVariable Long idUsuario,
                                             @PathVariable Long idEvento) {
        return Map.of("guardado", favoritoService.estaGuardado(idUsuario, idEvento));
    }

    // Guarda o quita según corresponda. Devuelve el estado resultante.
    @PostMapping("/alternar")
    public Map<String, Boolean> alternar(@RequestBody Map<String, Long> cuerpo) {
        boolean guardado = favoritoService.alternar(
                cuerpo.get("idUsuario"),
                cuerpo.get("idEvento")
        );
        return Map.of("guardado", guardado);
    }
}
