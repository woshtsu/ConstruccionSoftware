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
        String sql = "SELECT c.idCotizacion, p.nombre AS NombreProveedor, m.nombre AS NombreMaterial, c.precioMaterial, p.url AS EnlaceCompra " +
                "FROM Cotizacion c INNER JOIN Proveedor p ON c.idProveedor = p.idProveedor " +
                "INNER JOIN Material m ON c.idMaterial = m.idMaterial ORDER BY c.ultimaFechaActualizada DESC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
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
        try (Connection conn = dataSource.getConnection()) {
            String idProveedor = obtenerProveedorId(conn, nombreProveedor, enlaceCompra);
            String sql = "INSERT INTO Cotizacion (idCotizacion, idMaterial, idProveedor, precioMaterial, ultimaFechaActualizada) " +
                    "OUTPUT inserted.idCotizacion VALUES (NEWID(), ?, ?, ?, GETDATE())";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, idMaterial);
                ps.setString(2, idProveedor);
                ps.setBigDecimal(3, precioMaterial);
                boolean hasResult = ps.execute();
                if (hasResult) {
                    try (ResultSet rs = ps.getResultSet()) {
                        if (rs.next()) return rs.getString(1);
                    }
                }
            }
        }
        throw new SQLException("Sin resultado en inserción de cotizacion");
    }

    private String obtenerProveedorId(Connection conn, String nombreProveedor, String enlaceCompra) throws SQLException {
        String byUrl = null;
        if (enlaceCompra != null && !enlaceCompra.isBlank()) {
            String sql1 = "SELECT TOP 1 idProveedor FROM Proveedor WHERE url = ? ORDER BY nombre";
            try (PreparedStatement ps = conn.prepareStatement(sql1)) {
                ps.setString(1, enlaceCompra);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) byUrl = rs.getString(1);
                }
            }
        }
        if (byUrl != null) return byUrl;

        String byNombre = null;
        if (nombreProveedor != null && !nombreProveedor.isBlank()) {
            String sql2 = "SELECT TOP 1 idProveedor FROM Proveedor WHERE nombre = ? ORDER BY nombre";
            try (PreparedStatement ps = conn.prepareStatement(sql2)) {
                ps.setString(1, nombreProveedor);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) byNombre = rs.getString(1);
                }
            }
        }
        if (byNombre != null) return byNombre;

        String insert = "INSERT INTO Proveedor (idProveedor, nombre, url) OUTPUT inserted.idProveedor VALUES (NEWID(), ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insert)) {
            ps.setString(1, nombreProveedor);
            ps.setString(2, enlaceCompra);
            boolean hasResult = ps.execute();
            if (hasResult) {
                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) return rs.getString(1);
                }
            }
        }
        throw new SQLException("Sin resultado al registrar proveedor");
    }
}
