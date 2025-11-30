package com.example.demo_api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Proforma")
public class Proforma {
    @Id
    @Column(name = "idProforma", length = 50)
    private String idProforma;

    @Column(name = "idAreaConstruccion", length = 50, nullable = false)
    private String idAreaConstruccion;

    @Column(name = "precioTotal", precision = 12, scale = 2)
    private BigDecimal precioTotal;

    @Column(name = "fechaCreacion")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "proforma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleProforma> detalles;

    // Constructores
    public Proforma() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Proforma(String idProforma, String idAreaConstruccion, BigDecimal precioTotal) {
        this.idProforma = idProforma;
        this.idAreaConstruccion = idAreaConstruccion;
        this.precioTotal = precioTotal;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y Setters
    public String getIdProforma() { return idProforma; }
    public void setIdProforma(String idProforma) { this.idProforma = idProforma; }

    public String getIdAreaConstruccion() { return idAreaConstruccion; }
    public void setIdAreaConstruccion(String idAreaConstruccion) { this.idAreaConstruccion = idAreaConstruccion; }

    public BigDecimal getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(BigDecimal precioTotal) { this.precioTotal = precioTotal; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public List<DetalleProforma> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleProforma> detalles) { this.detalles = detalles; }
}
