package com.horizon.backend.services;

import com.horizon.backend.models.Ruta;
import com.horizon.backend.models.Guia;
import com.horizon.backend.models.ZonaEstadistica;
import com.horizon.backend.models.Usuario;
import com.horizon.backend.repositories.ZonaRepository;
import com.horizon.backend.repositories.UsuarioRepository;
import com.horizon.backend.repositories.GuiaRepository;
import com.horizon.backend.repositories.EventoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ZonaService {

    // Valores permitidos en la columna nivel_dificultad según el esquema.
    private static final List<String> DIFICULTADES = List.of("FACIL", "MEDIO", "DIFICIL");

    private static final int ID_ROL_GUIA = 2;

    private final ZonaRepository zonaRepository;
    private final UsuarioRepository usuarioRepository;
    private final GuiaRepository guiaRepository;
    private final EventoRepository eventoRepository;

    public ZonaService(ZonaRepository zonaRepository,
                       UsuarioRepository usuarioRepository,
                       GuiaRepository guiaRepository,
                       EventoRepository eventoRepository) {
        this.zonaRepository = zonaRepository;
        this.usuarioRepository = usuarioRepository;
        this.guiaRepository = guiaRepository;
        this.eventoRepository = eventoRepository;
    }

    // Obtiene una lista de todas las rutas (zonas).
    public List<Ruta> obtenerTodas() {
        return zonaRepository.findAll();
    }

    // Obtiene una ruta (zona) por su ID.
    public Ruta obtenerPorId(Long id) {
        return zonaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ruta no encontrada con ID: " + id));
    }

    /**
     * Comprueba que el idGuia corresponda a un guía real cuyo usuario
     * tiene el rol correspondiente. Sin esto, cualquiera podría crear
     * zonas a nombre de otro simplemente enviando un idGuia distinto.
     */
    private void validarQueSeaGuia(Long idGuia) {
        if (idGuia == null) {
            throw new IllegalArgumentException("Se requiere un ID de guía para crear o modificar una zona.");
        }

        Guia guia = guiaRepository.findById(idGuia)
                .orElseThrow(() -> new IllegalArgumentException("El guía con ID " + idGuia + " no existe."));

        Usuario usuario = usuarioRepository.findById(guia.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("El usuario asociado al guía no fue encontrado."));

        if (usuario.getIdRol() != ID_ROL_GUIA) {
            throw new IllegalArgumentException("El usuario no tiene el rol de Guía y no puede administrar zonas.");
        }
    }

    /**
     * Valida los datos de la zona. Las coordenadas se comprueban contra
     * el rango real del planeta: un error de dedo colocaría el pin en
     * medio del océano sin que nadie se entere hasta ver el mapa.
     */
    private void validarDatos(Ruta ruta) {
        if (ruta.getNombre() == null || ruta.getNombre().isBlank()) {
            throw new IllegalArgumentException("La zona necesita un nombre.");
        }

        if (ruta.getNivelDificultad() != null
                && !DIFICULTADES.contains(ruta.getNivelDificultad().toUpperCase())) {
            throw new IllegalArgumentException(
                    "Nivel de dificultad no válido: " + ruta.getNivelDificultad() + ". Usa FACIL, MEDIO o DIFICIL.");
        }

        BigDecimal lat = ruta.getLatitud();
        BigDecimal lng = ruta.getLongitud();

        if (lat != null && (lat.doubleValue() < -90 || lat.doubleValue() > 90)) {
            throw new IllegalArgumentException("La latitud debe estar entre -90 y 90.");
        }

        if (lng != null && (lng.doubleValue() < -180 || lng.doubleValue() > 180)) {
            throw new IllegalArgumentException("La longitud debe estar entre -180 y 180.");
        }

        // Sin coordenadas la zona existe en la base pero es invisible en
        // el mapa, que es donde los exploradores la descubren.
        if (lat == null || lng == null) {
            throw new IllegalArgumentException("La zona necesita latitud y longitud para aparecer en el mapa.");
        }
    }

    // Guarda una nueva ruta (zona).
    public Ruta guardar(Ruta ruta) {
        validarQueSeaGuia(ruta.getIdGuia());
        validarDatos(ruta);

        if (ruta.getNivelDificultad() != null) {
            ruta.setNivelDificultad(ruta.getNivelDificultad().toUpperCase());
        }

        return zonaRepository.save(ruta);
    }

    /**
     * Actualiza una zona existente.
     * Copia TODOS los campos editables: la versión anterior sólo movía
     * cuatro, así que descripción, deporte y coordenadas quedaban
     * congeladas aunque el formulario las enviara.
     */
    public Ruta actualizar(Long id, Ruta rutaActualizada) {
        Ruta ruta = obtenerPorId(id);

        // Sólo el guía dueño de la zona puede modificarla.
        if (ruta.getIdGuia() != null && !ruta.getIdGuia().equals(rutaActualizada.getIdGuia())) {
            throw new IllegalArgumentException("Esta zona pertenece a otro guía.");
        }

        validarQueSeaGuia(rutaActualizada.getIdGuia());
        validarDatos(rutaActualizada);

        ruta.setNombre(rutaActualizada.getNombre());
        ruta.setUbicacion(rutaActualizada.getUbicacion());
        ruta.setNivelDificultad(rutaActualizada.getNivelDificultad().toUpperCase());
        ruta.setDescripcion(rutaActualizada.getDescripcion());
        ruta.setIdDeporte(rutaActualizada.getIdDeporte());
        ruta.setLatitud(rutaActualizada.getLatitud());
        ruta.setLongitud(rutaActualizada.getLongitud());
        ruta.setIdGuia(rutaActualizada.getIdGuia());

        return zonaRepository.save(ruta);
    }

    /**
     * Elimina una zona.
     *
     * Se comprueban dos cosas antes de borrar. Primera, que quien
     * elimina sea el guía dueño: sin esto cualquiera podría borrar las
     * zonas de otro con una sola petición.
     *
     * Segunda, que no tenga eventos programados. La llave foránea de
     * `evento` lo impediría de todas formas, pero el error de MySQL
     * llega como un 500 ilegible; es mejor explicar qué pasa y cuántos
     * eventos hay que mover primero.
     */
    public void eliminar(Long id, Long idGuia) {
        Ruta zona = obtenerPorId(id);

        validarQueSeaGuia(idGuia);

        if (zona.getIdGuia() == null || !zona.getIdGuia().equals(idGuia)) {
            throw new IllegalArgumentException("Esta zona pertenece a otro guía.");
        }

        long eventos = eventoRepository.countByIdZona(id);
        if (eventos > 0) {
            throw new IllegalArgumentException(
                    "No puedes eliminar esta zona: tiene " + eventos
                    + (eventos == 1 ? " evento programado." : " eventos programados.")
                    + " Elimina o reprograma esos eventos primero.");
        }

        zonaRepository.deleteById(id);
    }

    // Obtiene las estadísticas de reservas por zona.
    public List<ZonaEstadistica> obtenerEstadisticas() {
        return zonaRepository.obtenerEstadisticasZonas();
    }
}
