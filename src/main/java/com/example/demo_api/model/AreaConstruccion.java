package com.example.demo_api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "AreaConstruccion")
public class AreaConstruccion {
    @Id
    @Column(name = "idAreaConstruccion", length = 50)
    private String idAreaConstruccion;

    @ManyToOne
    @JoinColumn(name = "idProyecto", nullable = false)
    private Proyecto proyecto;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "largo", nullable = false, precision = 10, scale = 2)
    private BigDecimal largo;

    @Column(name = "alto", nullable = false, precision = 10, scale = 2)
    private BigDecimal alto;

    @Column(name = "espesor", nullable = false, precision = 10, scale = 2)
    private BigDecimal espesor;

    @Column(name = "superficie", nullable = false, precision = 10, scale = 2)
    private BigDecimal superficie;

    @Column(name = "archivoImportado", length = 255)
    private String archivoImportado;

    // Constructores
    public AreaConstruccion() {}

    public AreaConstruccion(String idAreaConstruccion, Proyecto proyecto, String tipo, 
                           BigDecimal largo, BigDecimal alto, BigDecimal espesor, 
                           BigDecimal superficie, String archivoImportado) {
        this.idAreaConstruccion = idAreaConstruccion;
        this.proyecto = proyecto;
        this.tipo = tipo;
        this.largo = largo;
        this.alto = alto;
        this.espesor = espesor;
        this.superficie = superficie;
        this.archivoImportado = archivoImportado;
    }

    // Getters y Setters
    public String getIdAreaConstruccion() { return idAreaConstruccion; }
    public void setIdAreaConstruccion(String idAreaConstruccion) { this.idAreaConstruccion = idAreaConstruccion; }

    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }

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
