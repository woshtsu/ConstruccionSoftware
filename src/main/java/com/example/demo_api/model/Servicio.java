package com.example.demo_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Servicio")
public class Servicio {
    @Id
    @Column(name = "idServicio", length = 50)
    private String idServicio;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Persona cliente;

    @ManyToOne
    @JoinColumn(name = "idAsesor", nullable = true)
    private Asesor asesor;

    @Column(name = "fechaInicio")
    private LocalDateTime fechaInicio;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proyecto> proyectos;

    // Constructores
    public Servicio() {
        this.fechaInicio = LocalDateTime.now();
    }

    public Servicio(String idServicio, Persona cliente, Asesor asesor) {
        this.idServicio = idServicio;
        this.cliente = cliente;
        this.asesor = asesor;
        this.fechaInicio = LocalDateTime.now();
    }

    // Getters y Setters
    public String getIdServicio() { return idServicio; }
    public void setIdServicio(String idServicio) { this.idServicio = idServicio; }

    public Persona getCliente() { return cliente; }
    public void setCliente(Persona cliente) { this.cliente = cliente; }

    public Asesor getAsesor() { return asesor; }
    public void setAsesor(Asesor asesor) { this.asesor = asesor; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public List<Proyecto> getProyectos() { return proyectos; }
    public void setProyectos(List<Proyecto> proyectos) { this.proyectos = proyectos; }
}
