package com.horizon.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "explorador")
public class Explorador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExplorador;
    private Long idUsuario;
    private String nivel;

    @Column(name = "foto_url")
    private String fotoUrl;

    // Constructor vacío requerido por JPA.
    public Explorador() {
    }

    // Setters y Getters para cada campo de la clase.
    public Long getIdExplorador() {
        return idExplorador;
    }

    public void setIdExplorador(Long idExplorador) {
        this.idExplorador = idExplorador;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}