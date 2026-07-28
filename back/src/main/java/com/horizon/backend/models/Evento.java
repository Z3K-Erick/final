package com.horizon.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
// Tabla asociada a la entidad Evento en la base de datos.
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Indica que el valor de la columna "idEvento" se generará automáticamente.
    private Long idEvento;

    private Long idDeporte;
    private Long idZona;

    // Datos que muestra la tarjeta del catálogo en el front.
    private String titulo;
    private String descripcion;

    private LocalDateTime fecha;

    // Texto libre ("6 Horas", "Día Completo"): el front lo imprime
    // tal cual y no se opera aritméticamente con él.
    private String duracion;

    // BigDecimal y no Double: en valores monetarios el punto flotante
    // introduce errores de redondeo inaceptables.
    private BigDecimal precio;

    private Integer capacidad;

    @Column(name = "foto_url")
    private String fotoUrl;

    public Evento() {
        // Constructor vacío requerido por JPA.
    }

    // Setters y Getters para cada campo de la clase.
    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public Long getIdDeporte() {
        return idDeporte;
    }

    public void setIdDeporte(Long idDeporte) {
        this.idDeporte = idDeporte;
    }

    public Long getIdZona() {
        return idZona;
    }

    public void setIdZona(Long idZona) {
        this.idZona = idZona;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
