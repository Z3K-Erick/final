package com.horizon.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario") // Cambiado a minúsculas por si acaso
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Long idUsuario;

    @Column(name = "idRol")
    private Integer idRol;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "correo")
    private String correo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "foto_url")
    private String fotoUrl;

    // Localidad declarada por el usuario. Se muestra en su perfil y,
    // en el caso de los guías, en su panel público.
    @Column(name = "ubicacion")
    private String ubicacion;

    // Constructor vacío requerido por JPA.
    public Usuario() {}

    // Constructor con todos los campos.
    public Usuario(Long idUsuario, Integer idRol, String nombre, String correo, String passwordHash) { // Constructor con todos los campos.
        this.idUsuario = idUsuario;
        this.idRol = idRol;
        this.nombre = nombre;
        this.correo = correo;
        this.passwordHash = passwordHash;
    }

    // Setters y Getters para cada campo de la clase.
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}