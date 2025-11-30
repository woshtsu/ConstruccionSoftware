package com.example.demo_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Proyecto")
public class Proyecto {
    @Id
    @Column(name = "idProyecto", length = 50)
    private String idProyecto;

    @ManyToOne
    @JoinColumn(name = "idServicio", nullable = false)
    private Servicio servicio;

    @OneToOne
    @JoinColumn(name = "idReporte", nullable = true)
    private Reporte reporte;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "VARCHAR(MAX)")
    private String descripcion;

    @Column(name = "fechaCreacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AreaConstruccion> areas;

    // Constructores
    public Proyecto() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Proyecto(String idProyecto, Servicio servicio, String titulo, String descripcion) {
        this.idProyecto = idProyecto;
        this.servicio = servicio;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y Setters
    public String getIdProyecto() { return idProyecto; }
    public void setIdProyecto(String idProyecto) { this.idProyecto = idProyecto; }

    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }

    public Reporte getReporte() { return reporte; }
    public void setReporte(Reporte reporte) { this.reporte = reporte; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public List<AreaConstruccion> getAreas() { return areas; }
    public void setAreas(List<AreaConstruccion> areas) { this.areas = areas; }
}
