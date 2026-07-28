package com.horizon.backend.controllers;

import com.horizon.backend.models.Resena;
import com.horizon.backend.services.ResenaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resenas")
// link http://localhost:8080/api/resenas
public class ResenaController {

    private final ResenaService resenaService;

    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    @PostMapping
    // Endpoint para crear una nueva reseña.
    public Resena crearResena(@RequestBody Resena resena) {
        return resenaService.crearResena(resena);
    }

    @GetMapping
    // Endpoint para obtener todas las reseñas.
    public java.util.List<Resena> obtenerResenas() {
        return resenaService.obtenerTodas();
    }
}