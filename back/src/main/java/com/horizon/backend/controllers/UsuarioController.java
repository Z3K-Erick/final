package com.horizon.backend.controllers;

import com.horizon.backend.models.Usuario;
import com.horizon.backend.services.UsuarioService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
// link http://localhost:8080/api/usuarios
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios() {
        // Devuelve una lista de todos los usuarios.
        // NOTA: En una aplicación real, deberías devolver un DTO en lugar de la entidad directamente.
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    // Endpoint para obtener un usuario por su ID.
    public Usuario obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id);
    }

    // Actualiza nombre, correo y foto. No modifica contraseña ni rol.
    @PutMapping("/{id}")
    public Usuario actualizarPerfil(@PathVariable Long id, @RequestBody Usuario datos) {
        return usuarioService.actualizarPerfil(id, datos);
    }

    // Cambia la contraseña. Requiere la actual para verificar identidad.
    @PutMapping("/{id}/password")
    public Map<String, String> cambiarPassword(@PathVariable Long id,
                                               @RequestBody Map<String, String> cuerpo) {
        usuarioService.cambiarPassword(id, cuerpo.get("passwordActual"), cuerpo.get("passwordNueva"));
        return Map.of("mensaje", "Contraseña actualizada correctamente.");
    }
}
