package com.example.demo_api.dto;

public class ProductoListadoDTO {
    private String idMaterial;
    private String titulo;
    private java.math.BigDecimal precio;
    private String url;

    public String getIdMaterial() { return idMaterial; }
    public void setIdMaterial(String idMaterial) { this.idMaterial = idMaterial; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public java.math.BigDecimal getPrecio() { return precio; }
    public void setPrecio(java.math.BigDecimal precio) { this.precio = precio; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
