package com.example.demo_api.dto;

import java.math.BigDecimal;

public class CotizacionComparacionDTO {
    private String idCotizacion;
    private String nombreProveedor;
    private String nombreMaterial;
    private BigDecimal precioMaterial;
    private String enlaceCompra;

    public String getIdCotizacion() { return idCotizacion; }
    public void setIdCotizacion(String idCotizacion) { this.idCotizacion = idCotizacion; }
    public String getNombreProveedor() { return nombreProveedor; }
    public void setNombreProveedor(String nombreProveedor) { this.nombreProveedor = nombreProveedor; }
    public String getNombreMaterial() { return nombreMaterial; }
    public void setNombreMaterial(String nombreMaterial) { this.nombreMaterial = nombreMaterial; }
    public BigDecimal getPrecioMaterial() { return precioMaterial; }
    public void setPrecioMaterial(BigDecimal precioMaterial) { this.precioMaterial = precioMaterial; }
    public String getEnlaceCompra() { return enlaceCompra; }
    public void setEnlaceCompra(String enlaceCompra) { this.enlaceCompra = enlaceCompra; }
}
