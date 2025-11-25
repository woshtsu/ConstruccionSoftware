package com.example.demo_api.dto;

public class RegistrarProyectoResponse {
    private String estado;
    private String idProyectoGenerado;
    private String mensaje;

    public RegistrarProyectoResponse() {}

    public RegistrarProyectoResponse(String estado, String idProyectoGenerado, String mensaje) {
        this.estado = estado;
        this.idProyectoGenerado = idProyectoGenerado;
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdProyectoGenerado() {
        return idProyectoGenerado;
    }

    public void setIdProyectoGenerado(String idProyectoGenerado) {
        this.idProyectoGenerado = idProyectoGenerado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
