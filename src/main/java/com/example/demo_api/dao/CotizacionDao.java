package com.example.demo_api.dao;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CotizacionDao {
    private final DataSource dataSource;

    public CotizacionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<com.example.demo_api.dto.CotizacionComparacionDTO> compararPorMaterial(String idMaterial) throws SQLException {
        List<com.example.demo_api.dto.CotizacionComparacionDTO> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             CallableStatement cs = conn.prepareCall("{call sp_CompararPreciosPorMaterial(?)}")) {
            cs.setString(1, idMaterial);
            boolean hasResult = cs.execute();
            if (hasResult) {
                try (ResultSet rs = cs.getResultSet()) {
                    while (rs.next()) {
                        com.example.demo_api.dto.CotizacionComparacionDTO dto = new com.example.demo_api.dto.CotizacionComparacionDTO();
                        dto.setIdCotizacion(rs.getString("idCotizacion"));
                        dto.setNombreProveedor(rs.getString("NombreProveedor"));
                        dto.setNombreMaterial(rs.getString("NombreMaterial"));
                        dto.setPrecioMaterial(rs.getBigDecimal("precioMaterial"));
                        dto.setEnlaceCompra(rs.getString("EnlaceCompra"));
                        list.add(dto);
                    }
                }
            }
        }
        return list;
    }

    public java.util.List<com.example.demo_api.dto.CotizacionComparacionDTO> listarTodas() throws SQLException {
        java.util.List<com.example.demo_api.dto.CotizacionComparacionDTO> list = new java.util.ArrayList<>();
        String sql = "SELECT idCotizacion, nombreProveedor, nombreMaterial, precioMaterial, enlaceCompra FROM Cotizacion ORDER BY ultimaFechaActualizada DESC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    com.example.demo_api.dto.CotizacionComparacionDTO dto = new com.example.demo_api.dto.CotizacionComparacionDTO();
                    dto.setIdCotizacion(rs.getString("idCotizacion"));
                    dto.setNombreProveedor(rs.getString("nombreProveedor"));
                    dto.setNombreMaterial(rs.getString("nombreMaterial"));
                    dto.setPrecioMaterial(rs.getBigDecimal("precioMaterial"));
                    dto.setEnlaceCompra(rs.getString("enlaceCompra"));
                    list.add(dto);
                }
            }
        }
        return list;
    }

    public int actualizarPrecio(String idCotizacion, java.math.BigDecimal precio) throws SQLException {
        String sql = "UPDATE Cotizacion SET precioMaterial = ?, ultimaFechaActualizada = GETDATE() WHERE idCotizacion = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, precio);
            ps.setString(2, idCotizacion);
            return ps.executeUpdate();
        }
    }

    public String crear(String idMaterial, String nombreProveedor, String nombreMaterial, java.math.BigDecimal precioMaterial, String enlaceCompra) throws SQLException {
        String sql = "INSERT INTO Cotizacion (idCotizacion, idMaterial, nombreProveedor, nombreMaterial, precioMaterial, enlaceCompra, ultimaFechaActualizada) OUTPUT inserted.idCotizacion VALUES (NEWID(), ?, ?, ?, ?, ?, GETDATE())";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idMaterial);
            ps.setString(2, nombreProveedor);
            ps.setString(3, nombreMaterial);
            ps.setBigDecimal(4, precioMaterial);
            ps.setString(5, enlaceCompra);
            boolean hasResult = ps.execute();
            if (hasResult) {
                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) return rs.getString(1);
                }
            }
            throw new SQLException("Sin resultado en inserción de cotizacion");
        }
    }
}
