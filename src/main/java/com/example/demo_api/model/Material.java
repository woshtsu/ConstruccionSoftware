package com.example.demo_api.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Material")
public class Material {
    @Id
    @Column(name = "idMaterial", length = 50)
    private String idMaterial;

    @Column(name = "idCategoria", length = 50, nullable = false)
    private String idCategoria;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "tipoUnidad", length = 20)
    private String tipoUnidad;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cotizacion> cotizaciones;

    // Constructores
    public Material() {}

    public Material(String idMaterial, String idCategoria, String nombre, String tipoUnidad) {
        this.idMaterial = idMaterial;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.tipoUnidad = tipoUnidad;
    }

    // Getters y Setters
    public String getIdMaterial() { return idMaterial; }
    public void setIdMaterial(String idMaterial) { this.idMaterial = idMaterial; }

    public String getIdCategoria() { return idCategoria; }
    public void setIdCategoria(String idCategoria) { this.idCategoria = idCategoria; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipoUnidad() { return tipoUnidad; }
    public void setTipoUnidad(String tipoUnidad) { this.tipoUnidad = tipoUnidad; }

    public List<Cotizacion> getCotizaciones() { return cotizaciones; }
    public void setCotizaciones(List<Cotizacion> cotizaciones) { this.cotizaciones = cotizaciones; }
}
