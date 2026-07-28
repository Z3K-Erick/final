package com.horizon.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "zona")
public class Ruta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idZona")
    private Long id;

    @Column(name = "nombre_zona")
    private String nombre;
    private String ubicacion;

    @Column(name = "nivel_dificultad")
    private String nivelDificultad; // Valores: FACIL, MEDIO, DIFICIL

    private String descripcion;

    private Long idGuia;
    private Long idDeporte;

    // Coordenadas para los pines del mapa (Leaflet) en el front.
    // BigDecimal y no Double: la columna es DECIMAL(10,7) y el punto
    // flotante introduce errores de redondeo en las coordenadas.
    private BigDecimal latitud;
    private BigDecimal longitud;

    // Constructor vacío requerido por JPA.
    public Ruta() {
    }

    // Constructor con todos los campos.
    public Ruta(Long id, String nombre, String ubicacion, String nivelDificultad,
                String descripcion, Long idGuia, Long idDeporte,
                BigDecimal latitud, BigDecimal longitud) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.nivelDificultad = nivelDificultad;
        this.descripcion = descripcion;
        this.idGuia = idGuia;
        this.idDeporte = idDeporte;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Setters y Getters para cada campo de la clase.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNivelDificultad() {
        return nivelDificultad;
    }

    public void setNivelDificultad(String nivelDificultad) {
        this.nivelDificultad = nivelDificultad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdGuia() {
        return idGuia;
    }

    public void setIdGuia(Long idGuia) {
        this.idGuia = idGuia;
    }

    public Long getIdDeporte() {
        return idDeporte;
    }

    public void setIdDeporte(Long idDeporte) {
        this.idDeporte = idDeporte;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }
}
