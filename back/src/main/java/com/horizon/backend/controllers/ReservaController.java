package com.horizon.backend.controllers;

import com.horizon.backend.models.Reserva;
import com.horizon.backend.services.ReservaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // Endpoint para obtener las reservas por explorador.
    @GetMapping("/explorador/{id}")
    public List<Reserva> obtenerPorExplorador(@PathVariable Long id) {
        return reservaService.obtenerPorExplorador(id);
    }

    // Endpoint para obtener las reservas ligadas a las clases/eventos de un guía.
    @GetMapping("/guia/{id}")
    public List<Reserva> obtenerPorGuia(@PathVariable Long id) {
        return reservaService.obtenerPorGuia(id);
    }

    @PostMapping
    // Endpoint para crear una nueva reserva.
    public Reserva crearReserva(@RequestBody Reserva reserva) {
        return reservaService.crearReserva(reserva);
    }

    // Cupos libres en un evento. Lo consulta la ficha de la actividad
    // para mostrar los lugares restantes antes de reservar.
    @GetMapping("/cupos/evento/{id}")
    public Map<String, Integer> cuposEvento(@PathVariable Long id) {
        return Map.of("disponibles", reservaService.cuposDisponiblesEvento(id));
    }

    // Cupos libres en una clase.
    @GetMapping("/cupos/clase/{id}")
    public Map<String, Integer> cuposClase(@PathVariable Long id) {
        return Map.of("disponibles", reservaService.cuposDisponiblesClase(id));
    }

    // Cancela una reserva. No la borra: la marca como CANCELADA
    // para conservar el histórico y liberar el cupo.
    @PutMapping("/{id}/cancelar")
    public Reserva cancelarReserva(@PathVariable Long id) {
        return reservaService.cancelarReserva(id);
    }

    // Cambia el estado de una reserva (lo usa el guía desde su panel).
    @PutMapping("/{id}/estado")
    public Reserva actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> cuerpo) {
        return reservaService.actualizarEstado(id, cuerpo.get("estado"));
    }
}
