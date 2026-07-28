package com.horizon.backend.services;

import com.horizon.backend.models.Reserva;
import com.horizon.backend.models.Resena;
import com.horizon.backend.repositories.ReservaRepository;
import com.horizon.backend.repositories.ResenaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ResenaService {

    private final ResenaRepository resenaRepository;
    private final ReservaRepository reservaRepository;

    public ResenaService(ResenaRepository resenaRepository, ReservaRepository reservaRepository) {
        this.resenaRepository = resenaRepository;
        this.reservaRepository = reservaRepository;
    }

    // Crea una nueva reseña en la base de datos.
    public Resena crearResena(Resena resena) {
        // Valida que la calificación se encuentre en el rango de 1 a 5.
        if (resena.getCalificacion() == null || resena.getCalificacion() < 1 || resena.getCalificacion() > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5.");
        }

        if (resena.getIdReserva() == null) {
            throw new IllegalArgumentException("La reseña debe estar asociada a una reserva.");
        }

        // idReserva es UNIQUE en la BD: evitamos que una segunda reseña reviente con un error 500.
        if (resenaRepository.existsByIdReserva(resena.getIdReserva())) {
            throw new IllegalArgumentException("Esta reserva ya tiene una reseña registrada.");
        }

        Reserva reserva = reservaRepository.findById(resena.getIdReserva())
                .orElseThrow(() -> new IllegalArgumentException("La reserva con ID " + resena.getIdReserva() + " no existe."));

        // Sólo se puede reseñar una reserva ya CONFIRMADA.
        if (!"CONFIRMADA".equals(reserva.getEstado())) {
            throw new IllegalArgumentException("Sólo se puede reseñar una reserva CONFIRMADA.");
        }

        // Establece la fecha actual antes de guardar la reseña.
        resena.setFecha(LocalDateTime.now());
        return resenaRepository.save(resena);
    }

    // Obtiene una lista de todas las reseñas.
    public java.util.List<Resena> obtenerTodas() {
        return resenaRepository.findAll();
    }
}