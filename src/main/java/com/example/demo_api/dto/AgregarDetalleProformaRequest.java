package com.example.demo_api.dto;

import java.math.BigDecimal;

public class AgregarDetalleProformaRequest {
    private String idProforma;
    private String idCotizacion;
    private String idAreaConstruccion;
    private BigDecimal cantidad;

    public String getIdProforma() { return idProforma; }
    public void setIdProforma(String idProforma) { this.idProforma = idProforma; }
    public String getIdCotizacion() { return idCotizacion; }
    public void setIdCotizacion(String idCotizacion) { this.idCotizacion = idCotizacion; }
    public String getIdAreaConstruccion() { return idAreaConstruccion; }
    public void setIdAreaConstruccion(String idAreaConstruccion) { this.idAreaConstruccion = idAreaConstruccion; }
    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }
}
