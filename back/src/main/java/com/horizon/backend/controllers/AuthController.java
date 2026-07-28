package com.horizon.backend.controllers;

import com.horizon.backend.models.Usuario;
import com.horizon.backend.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
// link http://localhost:8080/api/auth
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService =  authService;
    }

    // Endpoint para inicio de sesión. Devuelve el Usuario autenticado (sin el hash, que es WRITE_ONLY).
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario usuario){
        Usuario usuarioAutenticado = authService.login(usuario.getCorreo(), usuario.getPasswordHash());

        if (usuarioAutenticado != null){
            return ResponseEntity.ok(usuarioAutenticado);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // Endpoint para registro de usuarios. Devuelve el Usuario creado (con su idUsuario ya asignado).
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registro(@RequestBody Usuario usuario){
        Usuario usuarioGuardado = authService.registrar(usuario);
        return ResponseEntity.ok(usuarioGuardado);
    }
}