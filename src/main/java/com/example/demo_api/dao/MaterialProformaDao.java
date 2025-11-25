package com.example.demo_api.dao;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MaterialProformaDao {
    private final DataSource dataSource;

    public MaterialProformaDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String agregarDetalle(String idProforma, String idCotizacion, String idAreaConstruccion, java.math.BigDecimal cantidad) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement cs = conn.prepareCall("{call sp_AgregarMaterialProforma(?, ?, ?, ?)}")) {
            cs.setString(1, idProforma);
            cs.setString(2, idCotizacion);
            cs.setString(3, idAreaConstruccion);
            cs.setBigDecimal(4, cantidad);
            cs.execute();
        }
        String sql = "SELECT TOP 1 idMaterialProforma FROM MaterialProforma WHERE idProforma = ? ORDER BY fecha DESC";
        String fallback = "SELECT TOP 1 idMaterialProforma FROM MaterialProforma WHERE idProforma = ? ORDER BY idMaterialProforma DESC";
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, idProforma);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return rs.getString(1);
                }
            } catch (SQLException ignore) {}
            try (PreparedStatement ps = conn.prepareStatement(fallback)) {
                ps.setString(1, idProforma);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return rs.getString(1);
                }
            }
        }
        return null;
    }

    public List<com.example.demo_api.dto.MaterialProformaDTO> listarPorProforma(String idProforma) throws SQLException {
        String sql = "SELECT idMaterialProforma, idProforma, idCotizacion, idAreaConstruccion, cantidad, subtotal FROM MaterialProforma WHERE idProforma = ?";
        List<com.example.demo_api.dto.MaterialProformaDTO> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idProforma);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    com.example.demo_api.dto.MaterialProformaDTO dto = new com.example.demo_api.dto.MaterialProformaDTO();
                    dto.setIdMaterialProforma(rs.getString("idMaterialProforma"));
                    dto.setIdProforma(rs.getString("idProforma"));
                    dto.setIdCotizacion(rs.getString("idCotizacion"));
                    dto.setIdAreaConstruccion(rs.getString("idAreaConstruccion"));
                    dto.setCantidad(rs.getBigDecimal("cantidad"));
                    dto.setSubtotal(rs.getBigDecimal("subtotal"));
                    list.add(dto);
                }
            }
        }
        return list;
    }
}
