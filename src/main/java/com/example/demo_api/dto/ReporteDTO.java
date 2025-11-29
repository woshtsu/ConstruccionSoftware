package com.example.demo_api.dto;

import java.sql.Timestamp;

public class ReporteDTO {
    private String idReporte;
    private Timestamp fecha;
    private String tipoFormato;

    public String getIdReporte() { return idReporte; }
    public void setIdReporte(String idReporte) { this.idReporte = idReporte; }
    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
    public String getTipoFormato() { return tipoFormato; }
    public void setTipoFormato(String tipoFormato) { this.tipoFormato = tipoFormato; }
}
