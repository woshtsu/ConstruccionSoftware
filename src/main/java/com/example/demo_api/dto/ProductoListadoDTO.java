package com.example.demo_api.dto;

import java.math.BigDecimal;

public class ProductoListadoDTO {
    private String titulo;
    private BigDecimal precio;
    private String url;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
