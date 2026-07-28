package com.horizon.backend.models;

// Interfaz utilizada por Spring Data JPA para mapear los resultados de una consulta nativa (proyección).
public interface ZonaEstadistica {
    String getNombreZona();
    Long getTotalReservas();
}