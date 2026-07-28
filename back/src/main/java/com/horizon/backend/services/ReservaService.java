package com.horizon.backend.services;

import com.horizon.backend.models.Clase;
import com.horizon.backend.models.Evento;
import com.horizon.backend.models.Reserva;
import com.horizon.backend.repositories.ClaseRepository;
import com.horizon.backend.repositories.EventoRepository;
import com.horizon.backend.repositories.ReservaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {

    // Estados que siguen ocupando un cupo (CANCELADA lo libera).
    private static final List<String> ESTADOS_QUE_OCUPAN_CUPO = List.of("PENDIENTE", "CONFIRMADA");

    private final ReservaRepository reservaRepository;
    private final ClaseRepository claseRepository;
    private final EventoRepository eventoRepository;

    public ReservaService(ReservaRepository reservaRepository, ClaseRepository claseRepository, EventoRepository eventoRepository) {
        this.reservaRepository = reservaRepository;
        this.claseRepository = claseRepository;
        this.eventoRepository = eventoRepository;
    }

    // Obtiene todas las reservas de un explorador específico.
    public List<Reserva> obtenerPorExplorador(Long idExplorador) {
        return reservaRepository.findByIdExplorador(idExplorador);
    }

    // Obtiene todas las reservas ligadas a las clases/eventos de un guía (para su dashboard).
    public List<Reserva> obtenerPorGuia(Long idGuia) {
        return reservaRepository.findByGuia(idGuia);
    }

    // Crea una nueva reserva en la base de datos.
    public Reserva crearReserva(Reserva reserva) {
        // Una reserva debe ser para una clase O un evento, pero no ambos ni ninguno.
        boolean tieneClase = reserva.getIdClase() != null;
        boolean tieneEvento = reserva.getIdEvento() != null;

        // Si ambas son verdaderas o ambas son falsas, la condición es inválida.
        if (tieneClase == tieneEvento) {
            throw new IllegalArgumentException("La reserva debe tener un idClase o un idEvento, pero no ambos ni estar vacíos.");
        }

        if (reserva.getIdExplorador() == null) {
            throw new IllegalArgumentException("La reserva debe tener un idExplorador.");
        }

        if (reserva.getPrecioReserva() == null) {
            throw new IllegalArgumentException("La reserva debe incluir un precio.");
        }

        // Valida que aún haya cupo disponible antes de crear la reserva.
        if (tieneClase) {
            Clase clase = claseRepository.findById(reserva.getIdClase())
                    .orElseThrow(() -> new IllegalArgumentException("La clase con ID " + reserva.getIdClase() + " no existe."));
            long ocupadas = reservaRepository.countByIdClaseAndEstadoIn(reserva.getIdClase(), ESTADOS_QUE_OCUPAN_CUPO);
            if (ocupadas >= clase.getCapacidad()) {
                throw new IllegalArgumentException("La clase ya no tiene cupos disponibles.");
            }
        } else {
            Evento evento = eventoRepository.findById(reserva.getIdEvento())
                    .orElseThrow(() -> new IllegalArgumentException("El evento con ID " + reserva.getIdEvento() + " no existe."));
            long ocupadas = reservaRepository.countByIdEventoAndEstadoIn(reserva.getIdEvento(), ESTADOS_QUE_OCUPAN_CUPO);
            if (ocupadas >= evento.getCapacidad()) {
                throw new IllegalArgumentException("El evento ya no tiene cupos disponibles.");
            }
        }

        // Establece la fecha actual y el estado por defecto antes de guardar.
        reserva.setFechaReserva(LocalDateTime.now());
        reserva.setEstado("PENDIENTE"); // Estado por defecto según la BD
        return reservaRepository.save(reserva);
    }

    /**
     * Cupos que quedan libres en un evento.
     * Se calcula con la misma regla que usa crearReserva, para que lo que
     * muestra la interfaz coincida con lo que el servidor va a aceptar.
     */
    public int cuposDisponiblesEvento(Long idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new IllegalArgumentException("El evento con ID " + idEvento + " no existe."));
        long ocupadas = reservaRepository.countByIdEventoAndEstadoIn(idEvento, ESTADOS_QUE_OCUPAN_CUPO);
        return (int) Math.max(0, evento.getCapacidad() - ocupadas);
    }

    // Cupos que quedan libres en una clase.
    public int cuposDisponiblesClase(Long idClase) {
        Clase clase = claseRepository.findById(idClase)
                .orElseThrow(() -> new IllegalArgumentException("La clase con ID " + idClase + " no existe."));
        long ocupadas = reservaRepository.countByIdClaseAndEstadoIn(idClase, ESTADOS_QUE_OCUPAN_CUPO);
        return (int) Math.max(0, clase.getCapacidad() - ocupadas);
    }

    /**
     * Cancela una reserva. No se borra el registro: se marca como CANCELADA
     * para conservar el histórico del explorador y liberar el cupo.
     */
    public Reserva cancelarReserva(Long idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new IllegalArgumentException("La reserva con ID " + idReserva + " no existe."));

        if ("CANCELADA".equals(reserva.getEstado())) {
            throw new IllegalArgumentException("Esta reserva ya estaba cancelada.");
        }

        reserva.setEstado("CANCELADA");
        return reservaRepository.save(reserva);
    }

    // Cambia el estado de una reserva. Lo usa el guía desde su panel.
    public Reserva actualizarEstado(Long idReserva, String nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("Debes indicar el nuevo estado.");
        }

        String estado = nuevoEstado.toUpperCase();
        if (!List.of("PENDIENTE", "CONFIRMADA", "CANCELADA").contains(estado)) {
            throw new IllegalArgumentException("Estado no válido: " + nuevoEstado);
        }

        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new IllegalArgumentException("La reserva con ID " + idReserva + " no existe."));

        reserva.setEstado(estado);
        return reservaRepository.save(reserva);
    }
}
