package com.example.demo_api.dto;

import java.math.BigDecimal;

public class AreaDTO {
    private String idAreaConstruccion;
    private String idProyecto;
    private String tipo;
    private BigDecimal largo;
    private BigDecimal alto;
    private BigDecimal espesor;
    private BigDecimal superficie;
    private String archivoImportado;

    public String getIdAreaConstruccion() { return idAreaConstruccion; }
    public void setIdAreaConstruccion(String idAreaConstruccion) { this.idAreaConstruccion = idAreaConstruccion; }
    public String getIdProyecto() { return idProyecto; }
    public void setIdProyecto(String idProyecto) { this.idProyecto = idProyecto; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public BigDecimal getLargo() { return largo; }
    public void setLargo(BigDecimal largo) { this.largo = largo; }
    public BigDecimal getAlto() { return alto; }
    public void setAlto(BigDecimal alto) { this.alto = alto; }
    public BigDecimal getEspesor() { return espesor; }
    public void setEspesor(BigDecimal espesor) { this.espesor = espesor; }
    public BigDecimal getSuperficie() { return superficie; }
    public void setSuperficie(BigDecimal superficie) { this.superficie = superficie; }
    public String getArchivoImportado() { return archivoImportado; }
    public void setArchivoImportado(String archivoImportado) { this.archivoImportado = archivoImportado; }
}
