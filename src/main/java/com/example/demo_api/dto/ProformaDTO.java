package com.example.demo_api.dto;

import java.sql.Timestamp;

public class ProformaDTO {
    private String idProforma;
    private String idProyecto;
    private Timestamp fecha;
    private java.math.BigDecimal costoTotal;

    public String getIdProforma() { return idProforma; }
    public void setIdProforma(String idProforma) { this.idProforma = idProforma; }
    public String getIdProyecto() { return idProyecto; }
    public void setIdProyecto(String idProyecto) { this.idProyecto = idProyecto; }
    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
    public java.math.BigDecimal getCostoTotal() { return costoTotal; }
    public void setCostoTotal(java.math.BigDecimal costoTotal) { this.costoTotal = costoTotal; }
}
