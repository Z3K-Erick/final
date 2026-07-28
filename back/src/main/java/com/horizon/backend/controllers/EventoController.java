package com.horizon.backend.controllers;

import com.horizon.backend.models.Evento;
import com.horizon.backend.services.EventoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
// link http://localhost:8080/api/eventos
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    // Endpoint para obtener todos los eventos.
    public List<Evento> obtenerEventos() {
        return eventoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    // Endpoint para obtener un evento por su ID.
    // Lo usa la página de detalle de ruta del front (ruta-detalle.html?id=).
    public ResponseEntity<Evento> obtenerEventoPorId(@PathVariable Long id) {
        Evento evento = eventoService.obtenerPorId(id);
        if (evento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(evento);
    }

    @PostMapping
    // Endpoint para crear un nuevo evento.
    public Evento crearEvento(@RequestBody Evento evento) {
        return eventoService.crearEvento(evento);
    }
}
