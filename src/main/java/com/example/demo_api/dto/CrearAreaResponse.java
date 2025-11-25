package com.example.demo_api.dto;

public class CrearAreaResponse {
    private String estado;
    private String idAreaConstruccion;
    private String mensaje;

    public CrearAreaResponse() {}

    public CrearAreaResponse(String estado, String idAreaConstruccion, String mensaje) {
        this.estado = estado;
        this.idAreaConstruccion = idAreaConstruccion;
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdAreaConstruccion() {
        return idAreaConstruccion;
    }

    public void setIdAreaConstruccion(String idAreaConstruccion) {
        this.idAreaConstruccion = idAreaConstruccion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
