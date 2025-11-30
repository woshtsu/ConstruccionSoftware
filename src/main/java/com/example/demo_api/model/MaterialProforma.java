package com.example.demo_api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "MaterialProforma")
public class MaterialProforma {
    @Id
    @Column(name = "idMaterialProforma", length = 50)
    private String idMaterialProforma;

    @ManyToOne
    @JoinColumn(name = "idProforma", nullable = false)
    private Proforma proforma;

    @ManyToOne
    @JoinColumn(name = "idCotizacion", nullable = false)
    private Cotizacion cotizacion;

    @ManyToOne
    @JoinColumn(name = "idAreaConstruccion", nullable = true)
    private AreaConstruccion areaConstruccion;

    @Column(name = "cantidad", precision = 18, scale = 2, nullable = false)
    private BigDecimal cantidad;

    @Column(name = "subtotal", precision = 18, scale = 2, nullable = false)
    private BigDecimal subtotal;

    // Constructores
    public MaterialProforma() {}

    public MaterialProforma(String idMaterialProforma, Proforma proforma, Cotizacion cotizacion, 
                           BigDecimal cantidad, BigDecimal subtotal) {
        this.idMaterialProforma = idMaterialProforma;
        this.proforma = proforma;
        this.cotizacion = cotizacion;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    // Getters y Setters
    public String getIdMaterialProforma() { return idMaterialProforma; }
    public void setIdMaterialProforma(String idMaterialProforma) { this.idMaterialProforma = idMaterialProforma; }

    public Proforma getProforma() { return proforma; }
    public void setProforma(Proforma proforma) { this.proforma = proforma; }

    public Cotizacion getCotizacion() { return cotizacion; }
    public void setCotizacion(Cotizacion cotizacion) { this.cotizacion = cotizacion; }

    public AreaConstruccion getAreaConstruccion() { return areaConstruccion; }
    public void setAreaConstruccion(AreaConstruccion areaConstruccion) { this.areaConstruccion = areaConstruccion; }

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
