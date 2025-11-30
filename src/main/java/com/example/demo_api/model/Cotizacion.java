package com.example.demo_api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Cotizacion")
public class Cotizacion {
    @Id
    @Column(name = "idCotizacion", length = 50)
    private String idCotizacion;

    @ManyToOne
    @JoinColumn(name = "idMaterial", nullable = false)
    private Material material;

    @Column(name = "nombreProveedor", length = 150)
    private String nombreProveedor;

    @Column(name = "nombreMaterial", length = 150)
    private String nombreMaterial;

    @Column(name = "precioMaterial", precision = 12, scale = 2)
    private BigDecimal precioMaterial;

    @Column(name = "enlaceCompra", columnDefinition = "VARCHAR(MAX)")
    private String enlaceCompra;

    // Constructores
    public Cotizacion() {}

    public Cotizacion(String idCotizacion, Material material, String nombreProveedor, 
                     String nombreMaterial, BigDecimal precioMaterial, String enlaceCompra) {
        this.idCotizacion = idCotizacion;
        this.material = material;
        this.nombreProveedor = nombreProveedor;
        this.nombreMaterial = nombreMaterial;
        this.precioMaterial = precioMaterial;
        this.enlaceCompra = enlaceCompra;
    }

    // Getters y Setters
    public String getIdCotizacion() { return idCotizacion; }
    public void setIdCotizacion(String idCotizacion) { this.idCotizacion = idCotizacion; }

    public Material getMaterial() { return material; }
    public void setMaterial(Material material) { this.material = material; }

    public String getNombreProveedor() { return nombreProveedor; }
    public void setNombreProveedor(String nombreProveedor) { this.nombreProveedor = nombreProveedor; }

    public String getNombreMaterial() { return nombreMaterial; }
    public void setNombreMaterial(String nombreMaterial) { this.nombreMaterial = nombreMaterial; }

    public BigDecimal getPrecioMaterial() { return precioMaterial; }
    public void setPrecioMaterial(BigDecimal precioMaterial) { this.precioMaterial = precioMaterial; }

    public String getEnlaceCompra() { return enlaceCompra; }
    public void setEnlaceCompra(String enlaceCompra) { this.enlaceCompra = enlaceCompra; }
}
