package com.example.demo_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reporte")
public class Reporte {
    @Id
    @Column(name = "idReporte", length = 50)
    private String idReporte;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "contenido", columnDefinition = "VARCHAR(MAX)")
    private String contenido;

    @Column(name = "tipoFormato", length = 10)
    private String tipoFormato;

    @OneToOne(mappedBy = "reporte")
    private Proyecto proyecto;

    // Constructores
    public Reporte() {
        this.fecha = LocalDateTime.now();
    }

    public Reporte(String idReporte, String contenido, String tipoFormato) {
        this.idReporte = idReporte;
        this.contenido = contenido;
        this.tipoFormato = tipoFormato;
        this.fecha = LocalDateTime.now();
    }

    // Getters y Setters
    public String getIdReporte() { return idReporte; }
    public void setIdReporte(String idReporte) { this.idReporte = idReporte; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public String getTipoFormato() { return tipoFormato; }
    public void setTipoFormato(String tipoFormato) { this.tipoFormato = tipoFormato; }

    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }
}
