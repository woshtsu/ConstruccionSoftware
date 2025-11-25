package com.example.demo_api.dto;

import java.math.BigDecimal;

public class AgregarDetalleProformaResponse {
    private String estado;
    private String mensaje;
    private String idMaterialProforma;
    private BigDecimal costoTotalActualizado;

    public AgregarDetalleProformaResponse() {}

    public AgregarDetalleProformaResponse(String estado, String mensaje, String idMaterialProforma, BigDecimal costoTotalActualizado) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.idMaterialProforma = idMaterialProforma;
        this.costoTotalActualizado = costoTotalActualizado;
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getIdMaterialProforma() { return idMaterialProforma; }
    public void setIdMaterialProforma(String idMaterialProforma) { this.idMaterialProforma = idMaterialProforma; }
    public BigDecimal getCostoTotalActualizado() { return costoTotalActualizado; }
    public void setCostoTotalActualizado(BigDecimal costoTotalActualizado) { this.costoTotalActualizado = costoTotalActualizado; }
}
