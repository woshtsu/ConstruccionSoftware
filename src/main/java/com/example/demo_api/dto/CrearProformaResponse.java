package com.example.demo_api.dto;

public class CrearProformaResponse {
    private String estado;
    private String idProforma;
    private String mensaje;

    public CrearProformaResponse() {}

    public CrearProformaResponse(String estado, String idProforma, String mensaje) {
        this.estado = estado;
        this.idProforma = idProforma;
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdProforma() {
        return idProforma;
    }

    public void setIdProforma(String idProforma) {
        this.idProforma = idProforma;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
