package com.example.demo_api.dto;

public class LoginResponse {
    private String estado;
    private String mensaje;
    private String token;
    private String idCliente;

    public LoginResponse() {}

    public LoginResponse(String estado, String mensaje, String token, String idCliente) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.token = token;
        this.idCliente = idCliente;
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getIdCliente() { return idCliente; }
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }
}
