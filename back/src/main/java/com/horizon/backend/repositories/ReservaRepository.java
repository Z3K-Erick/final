package com.horizon.backend.repositories;

import com.horizon.backend.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByIdExplorador(Long idExplorador);

    // Usadas para calcular cupos disponibles: sólo cuentan las reservas que ocupan un lugar
    // (normalmente PENDIENTE y CONFIRMADA; CANCELADA libera el cupo).
    long countByIdClaseAndEstadoIn(Long idClase, Collection<String> estados);

    long countByIdEventoAndEstadoIn(Long idEvento, Collection<String> estados);

    /**
     * Todas las reservas ligadas a las Clases o Eventos (vía Zona) de un guía.
     * Alimenta el Dashboard del Guía: rutas activas, ganancias y reseñas de clientes.
     */
    @Query(value = "SELECT r.* FROM reserva r " +
                   "LEFT JOIN clase c ON r.idClase = c.idClase " +
                   "LEFT JOIN evento e ON r.idEvento = e.idEvento " +
                   "LEFT JOIN zona z ON e.idZona = z.idZona " +
                   "WHERE c.idGuia = :idGuia OR z.idGuia = :idGuia", nativeQuery = true)
    List<Reserva> findByGuia(@Param("idGuia") Long idGuia);
}