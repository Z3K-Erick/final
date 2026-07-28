package com.horizon.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "deporte")
public class Deporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeporte;

    private String tipo;

    // Constructor vacío requerido por JPA.
    public Deporte() {}

    // Constructor con todos los campos.
    public Deporte(Long idDeporte, String tipo) {
        this.idDeporte = idDeporte;
        this.tipo = tipo;
    }

    // Setters y Getters para cada campo de la clase.
    public Long getIdDeporte() {
        return idDeporte;
    }

    public void setIdDeporte(Long idDeporte) {
        this.idDeporte = idDeporte;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
