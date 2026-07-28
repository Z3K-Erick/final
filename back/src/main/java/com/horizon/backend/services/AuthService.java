package com.horizon.backend.services;

import com.horizon.backend.models.Explorador;
import com.horizon.backend.models.Guia;
import com.horizon.backend.models.Usuario;
import com.horizon.backend.repositories.ExploradorRepository;
import com.horizon.backend.repositories.GuiaRepository;
import com.horizon.backend.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final GuiaRepository guiaRepository;
    private final ExploradorRepository exploradorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository,
                       GuiaRepository guiaRepository,
                       ExploradorRepository exploradorRepository,
                       PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.guiaRepository = guiaRepository;
        this.exploradorRepository = exploradorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * La operación es transaccional: si no se completan todos los pasos, o no se hace ninguno.
     */
    @Transactional
    public Usuario registrar(Usuario usuario) {
        // CAMBIO 1: valida que idRol no venga nulo (evita NullPointerException más abajo).
        if (usuario.getIdRol() == null) {
            throw new IllegalArgumentException("El idRol es obligatorio.");
        }

        // Valida si el correo ya existe para evitar duplicados.
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            // CAMBIO 2: era RuntimeException (daba 500). Ahora da 400.
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        // Codifica la contraseña antes de guardarla.
        String hash = passwordEncoder.encode(usuario.getPasswordHash());
        usuario.setPasswordHash(hash);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Crea un perfil de Explorador o Guía según el rol del usuario.
        if (usuario.getIdRol() == 1) { // 1 = EXPLORADOR[cite: 2]
            Explorador explorador = new Explorador();
            explorador.setIdUsuario(usuarioGuardado.getIdUsuario());
            exploradorRepository.save(explorador);
        } else if (usuario.getIdRol() == 2) { // 2 = GUIA[cite: 2]
            Guia guia = new Guia();
            guia.setIdUsuario(usuarioGuardado.getIdUsuario());
            guiaRepository.save(guia);
        }

        return usuarioGuardado;
    }

    /**
     * Valida las credenciales para el inicio de sesión.
     * @return el Usuario autenticado, o null si el correo no existe o la contraseña no coincide.
     */
    public Usuario login(String correo, String passwordPlana) {
        Usuario usuarioBd = usuarioRepository.findByCorreo(correo);

        if (usuarioBd == null) {
            return null;
        }

        // Compara la contraseña en texto plano con el hash almacenado.
        if (!passwordEncoder.matches(passwordPlana, usuarioBd.getPasswordHash())) {
            return null;
        }

        return usuarioBd;
    }
}