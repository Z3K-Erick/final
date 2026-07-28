package com.horizon.backend.controllers;

import com.horizon.backend.models.Usuario;
import com.horizon.backend.security.JwtService;
import com.horizon.backend.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService){
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Usuario usuario){
        Usuario usuarioAutenticado = authService.login(usuario.getCorreo(), usuario.getPasswordHash());

        if (usuarioAutenticado != null){
            String token = jwtService.generarToken(usuarioAutenticado.getIdUsuario(), usuarioAutenticado.getIdRol());

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("token", token);
            respuesta.put("usuario", usuarioAutenticado);

            return ResponseEntity.ok(respuesta);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registro(@RequestBody Usuario usuario){
        Usuario usuarioGuardado = authService.registrar(usuario);
        return ResponseEntity.ok(usuarioGuardado);
    }
}