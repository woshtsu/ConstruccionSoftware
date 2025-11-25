package com.example.demo_api.dto;

import java.math.BigDecimal;

public class MaterialProformaDTO {
    private String idMaterialProforma;
    private String idProforma;
    private String idCotizacion;
    private String idAreaConstruccion;
    private BigDecimal cantidad;
    private BigDecimal subtotal;

    public String getIdMaterialProforma() { return idMaterialProforma; }
    public void setIdMaterialProforma(String idMaterialProforma) { this.idMaterialProforma = idMaterialProforma; }
    public String getIdProforma() { return idProforma; }
    public void setIdProforma(String idProforma) { this.idProforma = idProforma; }
    public String getIdCotizacion() { return idCotizacion; }
    public void setIdCotizacion(String idCotizacion) { this.idCotizacion = idCotizacion; }
    public String getIdAreaConstruccion() { return idAreaConstruccion; }
    public void setIdAreaConstruccion(String idAreaConstruccion) { this.idAreaConstruccion = idAreaConstruccion; }
    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
