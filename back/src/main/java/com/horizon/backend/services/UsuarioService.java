package com.horizon.backend.services;

import com.horizon.backend.models.Usuario;
import com.horizon.backend.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Obtiene una lista de todos los usuarios registrados.
     * @return Lista de entidades Usuario.
     */
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    /**
     * Obtiene un usuario por su ID.
     */
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));
    }

    /**
     * Actualiza los datos editables del perfil: nombre, correo y foto.
     *
     * No toca la contraseña ni el rol. Cambiar el rol desde aquí
     * dejaría al usuario sin su perfil extendido (explorador o guía),
     * que se crea una sola vez durante el registro.
     */
    public Usuario actualizarPerfil(Long id, Usuario datos) {
        Usuario usuario = obtenerPorId(id);

        if (datos.getNombre() == null || datos.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre no puede quedar vacío.");
        }

        if (datos.getCorreo() == null || datos.getCorreo().isBlank()) {
            throw new IllegalArgumentException("El correo no puede quedar vacío.");
        }

        String correoNuevo = datos.getCorreo().trim();

        // El correo es la credencial de acceso: si cambia, hay que
        // comprobar que no lo esté usando otra cuenta.
        if (!correoNuevo.equalsIgnoreCase(usuario.getCorreo())
                && usuarioRepository.existsByCorreo(correoNuevo)) {
            throw new IllegalArgumentException("Ese correo ya está registrado por otra cuenta.");
        }

        usuario.setNombre(datos.getNombre().trim());
        usuario.setCorreo(correoNuevo);
        usuario.setFotoUrl(datos.getFotoUrl());
        usuario.setUbicacion(datos.getUbicacion());

        return usuarioRepository.save(usuario);
    }

    /**
     * Cambia la contraseña. Exige la actual: sin autenticación por token,
     * es la única garantía de que quien pide el cambio es el dueño de la cuenta.
     */
    public void cambiarPassword(Long id, String passwordActual, String passwordNueva) {
        Usuario usuario = obtenerPorId(id);

        if (passwordActual == null || passwordNueva == null) {
            throw new IllegalArgumentException("Debes indicar la contraseña actual y la nueva.");
        }

        if (!passwordEncoder.matches(passwordActual, usuario.getPasswordHash())) {
            throw new IllegalArgumentException("La contraseña actual no es correcta.");
        }

        if (passwordNueva.length() < 6) {
            throw new IllegalArgumentException("La contraseña nueva debe tener al menos 6 caracteres.");
        }

        usuario.setPasswordHash(passwordEncoder.encode(passwordNueva));
        usuarioRepository.save(usuario);
    }
}
