package com.horizon.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    // Endpoint de prueba para verificar que el servidor está funcionando.
    @GetMapping("/api/test")
    public String probarServidor() {
        return "¡El backend de la plataforma está vivo y funcionando!";
    }
}