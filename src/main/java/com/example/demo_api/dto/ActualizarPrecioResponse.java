package com.example.demo_api.dto;

import java.math.BigDecimal;

public class ActualizarPrecioResponse {
    private String estado;
    private String mensaje;
    private String idCotizacion;
    private BigDecimal precioMaterial;

    public ActualizarPrecioResponse() {}

    public ActualizarPrecioResponse(String estado, String mensaje, String idCotizacion, BigDecimal precioMaterial) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.idCotizacion = idCotizacion;
        this.precioMaterial = precioMaterial;
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getIdCotizacion() { return idCotizacion; }
    public void setIdCotizacion(String idCotizacion) { this.idCotizacion = idCotizacion; }
    public BigDecimal getPrecioMaterial() { return precioMaterial; }
    public void setPrecioMaterial(BigDecimal precioMaterial) { this.precioMaterial = precioMaterial; }
}
