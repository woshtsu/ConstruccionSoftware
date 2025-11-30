package com.example.demo_api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Proforma")
public class Proforma {
    @Id
    @Column(name = "idProforma", length = 50)
    private String idProforma;

    @ManyToOne
    @JoinColumn(name = "idProyecto", nullable = false)
    private Proyecto proyecto;

    @Column(name = "costoTotal", precision = 18, scale = 2)
    private BigDecimal costoTotal;

    @Column(name = "fechaCreacion")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "proforma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaterialProforma> materiales;

    // Constructores
    public Proforma() {
        this.fechaCreacion = LocalDateTime.now();
        this.costoTotal = BigDecimal.ZERO;
    }

    public Proforma(String idProforma, Proyecto proyecto, BigDecimal costoTotal) {
        this.idProforma = idProforma;
        this.proyecto = proyecto;
        this.costoTotal = costoTotal;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y Setters
    public String getIdProforma() { return idProforma; }
    public void setIdProforma(String idProforma) { this.idProforma = idProforma; }

    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }

    public BigDecimal getCostoTotal() { return costoTotal; }
    public void setCostoTotal(BigDecimal costoTotal) { this.costoTotal = costoTotal; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public List<MaterialProforma> getMateriales() { return materiales; }
    public void setMateriales(List<MaterialProforma> materiales) { this.materiales = materiales; }
}
