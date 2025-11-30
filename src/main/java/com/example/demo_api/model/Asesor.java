package com.example.demo_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Asesor")
public class Asesor {
    @Id
    @Column(name = "idAsesor", length = 50)
    private String idAsesor;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "password", length = 255)
    private String password;

    // Constructores
    public Asesor() {}

    public Asesor(String idAsesor, String nombre, String apellidos, String telefono, String email, String password) {
        this.idAsesor = idAsesor;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
    }

    // Getters y Setters
    public String getIdAsesor() { return idAsesor; }
    public void setIdAsesor(String idAsesor) { this.idAsesor = idAsesor; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
