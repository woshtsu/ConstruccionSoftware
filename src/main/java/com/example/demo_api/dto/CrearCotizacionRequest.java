package com.example.demo_api.dto;

import java.math.BigDecimal;

public class CrearCotizacionRequest {
    private String idMaterial;
    private String nombreProveedor;
    private String nombreMaterial;
    private BigDecimal precioMaterial;
    private String enlaceCompra;

    public String getIdMaterial() { return idMaterial; }
    public void setIdMaterial(String idMaterial) { this.idMaterial = idMaterial; }
    public String getNombreProveedor() { return nombreProveedor; }
    public void setNombreProveedor(String nombreProveedor) { this.nombreProveedor = nombreProveedor; }
    public String getNombreMaterial() { return nombreMaterial; }
    public void setNombreMaterial(String nombreMaterial) { this.nombreMaterial = nombreMaterial; }
    public BigDecimal getPrecioMaterial() { return precioMaterial; }
    public void setPrecioMaterial(BigDecimal precioMaterial) { this.precioMaterial = precioMaterial; }
    public String getEnlaceCompra() { return enlaceCompra; }
    public void setEnlaceCompra(String enlaceCompra) { this.enlaceCompra = enlaceCompra; }
}
