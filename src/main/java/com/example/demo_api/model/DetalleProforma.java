package com.example.demo_api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "DetalleProforma")
public class DetalleProforma {
    @Id
    @Column(name = "idDetalleProforma", length = 50)
    private String idDetalleProforma;

    @ManyToOne
    @JoinColumn(name = "idProforma", nullable = false)
    private Proforma proforma;

    @Column(name = "idCotizacion", length = 50, nullable = false)
    private String idCotizacion;

    @Column(name = "cantidad", precision = 10, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal;

    // Constructores
    public DetalleProforma() {}

    public DetalleProforma(String idDetalleProforma, Proforma proforma, String idCotizacion, 
                          BigDecimal cantidad, BigDecimal subtotal) {
        this.idDetalleProforma = idDetalleProforma;
        this.proforma = proforma;
        this.idCotizacion = idCotizacion;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    // Getters y Setters
    public String getIdDetalleProforma() { return idDetalleProforma; }
    public void setIdDetalleProforma(String idDetalleProforma) { this.idDetalleProforma = idDetalleProforma; }

    public Proforma getProforma() { return proforma; }
    public void setProforma(Proforma proforma) { this.proforma = proforma; }

    public String getIdCotizacion() { return idCotizacion; }
    public void setIdCotizacion(String idCotizacion) { this.idCotizacion = idCotizacion; }

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
