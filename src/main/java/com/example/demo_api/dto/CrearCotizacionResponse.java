package com.example.demo_api.dto;

public class CrearCotizacionResponse {
    private String estado;
    private String idCotizacion;
    private String mensaje;

    public CrearCotizacionResponse() {}

    public CrearCotizacionResponse(String estado, String idCotizacion, String mensaje) {
        this.estado = estado;
        this.idCotizacion = idCotizacion;
        this.mensaje = mensaje;
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getIdCotizacion() { return idCotizacion; }
    public void setIdCotizacion(String idCotizacion) { this.idCotizacion = idCotizacion; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
