package com.example.demo_api.dao;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class ProformaDao {
    private final DataSource dataSource;

    public ProformaDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String crear(String idProyecto) throws SQLException {
        String sql = "INSERT INTO Proforma (idProforma, idProyecto, fecha, costoTotal) OUTPUT inserted.idProforma VALUES (NEWID(), ?, GETDATE(), 0)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idProyecto);
            boolean hasResult = ps.execute();
            if (hasResult) {
                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) {
                        return rs.getString(1);
                    }
                }
            }
            throw new SQLException("Sin resultado en inserción de proforma");
        }
    }

    public java.util.List<com.example.demo_api.dto.ProformaDTO> listarPorProyecto(String idProyecto) throws SQLException {
        String sql = "SELECT idProforma, idProyecto, fecha, costoTotal FROM Proforma WHERE idProyecto = ?";
        java.util.List<com.example.demo_api.dto.ProformaDTO> list = new java.util.ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idProyecto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    com.example.demo_api.dto.ProformaDTO dto = new com.example.demo_api.dto.ProformaDTO();
                    dto.setIdProforma(rs.getString("idProforma"));
                    dto.setIdProyecto(rs.getString("idProyecto"));
                    dto.setFecha(rs.getTimestamp("fecha"));
                    dto.setCostoTotal(rs.getBigDecimal("costoTotal"));
                    list.add(dto);
                }
            }
        }
        return list;
    }

    public com.example.demo_api.dto.ProformaDTO obtenerPorId(String idProforma) throws SQLException {
        String sql = "SELECT idProforma, idProyecto, fecha, costoTotal FROM Proforma WHERE idProforma = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idProforma);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    com.example.demo_api.dto.ProformaDTO dto = new com.example.demo_api.dto.ProformaDTO();
                    dto.setIdProforma(rs.getString("idProforma"));
                    dto.setIdProyecto(rs.getString("idProyecto"));
                    dto.setFecha(rs.getTimestamp("fecha"));
                    dto.setCostoTotal(rs.getBigDecimal("costoTotal"));
                    return dto;
                }
            }
        }
        return null;
    }
}
