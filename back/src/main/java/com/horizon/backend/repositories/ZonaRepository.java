package com.horizon.backend.repositories;

import com.horizon.backend.models.Ruta;
import com.horizon.backend.models.ZonaEstadistica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZonaRepository extends JpaRepository<Ruta, Long> {

    /**
     * Consulta nativa para obtener estadísticas de las zonas más populares.
     * - Cuenta el número total de reservas 'CONFIRMADAS' para cada zona.
     * - Une las tablas ZONA, EVENTO y RESERVA.
     * - Agrupa los resultados por zona y los ordena de mayor a menor número de reservas.
     */
    @Query(value = "SELECT z.nombre_zona AS nombreZona, COUNT(r.idReserva) AS totalReservas " +
                   "FROM zona z " +
                   "JOIN evento e ON e.idZona = z.idZona " +
                   "JOIN reserva r ON r.idEvento = e.idEvento " +
                   "WHERE r.estado = 'CONFIRMADA' " + // Asegúrate de tener datos con este estado para el demo
                   "GROUP BY z.idZona, z.nombre_zona " +
                   "ORDER BY totalReservas DESC", nativeQuery = true)
    List<ZonaEstadistica> obtenerEstadisticasZonas();
}