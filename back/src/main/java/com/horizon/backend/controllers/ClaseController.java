package com.horizon.backend.controllers;

import com.horizon.backend.models.Clase;
import com.horizon.backend.services.ClaseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clases")
// link http://localhost:8080/api/clases
public class ClaseController {

    private final ClaseService claseService;

    public ClaseController(ClaseService claseService) {
        this.claseService = claseService;
    }

    @GetMapping
    // Endpoint para obtener todas las clases.
    public List<Clase> obtenerClases() {
        return claseService.obtenerTodas();
    }

    @PostMapping
    // Endpoint para crear una nueva clase.
    public Clase crearClase(@RequestBody Clase clase) {
        return claseService.crearClase(clase);
    }
}