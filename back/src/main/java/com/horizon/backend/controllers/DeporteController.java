package com.horizon.backend.controllers;

import com.horizon.backend.models.Deporte;
import com.horizon.backend.services.DeporteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/deportes")
// link http://localhost:8080/api/deportes
public class DeporteController {

    private final DeporteService deporteService;

    public DeporteController(DeporteService deporteService) {
        this.deporteService = deporteService;
    }

    // Endpoint para obtener la lista de deportes.
    @GetMapping
    public List<Deporte> obtenerDeportes() {
        return deporteService.obtenerTodos();
    }
}