package com.example.demo_api.dao;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class AreaDao {
    private final DataSource dataSource;

    public AreaDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String crear(String idProyecto, String tipo, java.math.BigDecimal largo, java.math.BigDecimal alto, java.math.BigDecimal espesor, java.math.BigDecimal superficie, String archivoImportado) throws SQLException {
        String sql = "INSERT INTO AreaConstruccion (idAreaConstruccion, idProyecto, tipo, largo, alto, espesor, superficie, archivoImportado) OUTPUT inserted.idAreaConstruccion VALUES (NEWID(), ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idProyecto);
            ps.setString(2, tipo);
            ps.setBigDecimal(3, largo);
            ps.setBigDecimal(4, alto);
            ps.setBigDecimal(5, espesor);
            ps.setBigDecimal(6, superficie);
            ps.setString(7, archivoImportado);
            boolean hasResult = ps.execute();
            if (hasResult) {
                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) {
                        return rs.getString(1);
                    }
                }
            }
            throw new SQLException("Sin resultado en inserción de área");
        }
    }

    public java.util.List<com.example.demo_api.dto.AreaDTO> listarPorProyecto(String idProyecto) throws SQLException {
        String sql = "SELECT idAreaConstruccion, idProyecto, tipo, largo, alto, espesor, superficie, archivoImportado FROM AreaConstruccion WHERE idProyecto = ?";
        java.util.List<com.example.demo_api.dto.AreaDTO> list = new java.util.ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idProyecto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    com.example.demo_api.dto.AreaDTO dto = new com.example.demo_api.dto.AreaDTO();
                    dto.setIdAreaConstruccion(rs.getString("idAreaConstruccion"));
                    dto.setIdProyecto(rs.getString("idProyecto"));
                    dto.setTipo(rs.getString("tipo"));
                    dto.setLargo(rs.getBigDecimal("largo"));
                    dto.setAlto(rs.getBigDecimal("alto"));
                    dto.setEspesor(rs.getBigDecimal("espesor"));
                    dto.setSuperficie(rs.getBigDecimal("superficie"));
                    dto.setArchivoImportado(rs.getString("archivoImportado"));
                    list.add(dto);
                }
            }
        }
        return list;
    }

    public com.example.demo_api.dto.AreaDTO obtenerPorId(String idArea) throws SQLException {
        String sql = "SELECT idAreaConstruccion, idProyecto, tipo, largo, alto, espesor, superficie, archivoImportado FROM AreaConstruccion WHERE idAreaConstruccion = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idArea);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    com.example.demo_api.dto.AreaDTO dto = new com.example.demo_api.dto.AreaDTO();
                    dto.setIdAreaConstruccion(rs.getString("idAreaConstruccion"));
                    dto.setIdProyecto(rs.getString("idProyecto"));
                    dto.setTipo(rs.getString("tipo"));
                    dto.setLargo(rs.getBigDecimal("largo"));
                    dto.setAlto(rs.getBigDecimal("alto"));
                    dto.setEspesor(rs.getBigDecimal("espesor"));
                    dto.setSuperficie(rs.getBigDecimal("superficie"));
                    dto.setArchivoImportado(rs.getString("archivoImportado"));
                    return dto;
                }
            }
        }
        return null;
    }
}
