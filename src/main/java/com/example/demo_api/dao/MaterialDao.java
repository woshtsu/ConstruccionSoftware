package com.example.demo_api.dao;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class MaterialDao {
    private final DataSource dataSource;

    public MaterialDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String obtenerIdPorNombre(String nombreMaterial) throws SQLException {
        String sql = "SELECT TOP 1 idMaterial FROM Material WHERE nombre = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreMaterial);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString(1);
            }
        }
        return null;
    }

    public String registrarSiNoExiste(String nombreMaterial) throws SQLException {
        String existente = obtenerIdPorNombre(nombreMaterial);
        if (existente != null) return existente;
        try (Connection conn = dataSource.getConnection()) {
            String idCategoria = obtenerCategoriaDefault(conn);
            if (idCategoria == null) throw new SQLException("No existe Categoria para registrar Material");
            String insert = "INSERT INTO Material (idMaterial, idCategoria, nombre, tipoUnidad) OUTPUT inserted.idMaterial VALUES (NEWID(), ?, ?, NULL)";
            try (PreparedStatement ps = conn.prepareStatement(insert)) {
                ps.setString(1, idCategoria);
                ps.setString(2, nombreMaterial);
                boolean hasResult = ps.execute();
                if (hasResult) {
                    try (ResultSet rs = ps.getResultSet()) {
                        if (rs.next()) return rs.getString(1);
                    }
                }
            }
        }
        throw new SQLException("Sin resultado en inserción de material");
    }

    private String obtenerCategoriaDefault(Connection conn) {
        String byNombre = "SELECT TOP 1 idCategoria FROM Categoria WHERE nombre LIKE ? ORDER BY nombre";
        try (PreparedStatement ps = conn.prepareStatement(byNombre)) {
            ps.setString(1, "%Material Seco%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString(1);
            }
        } catch (SQLException ignore) {}
        String any = "SELECT TOP 1 idCategoria FROM Categoria ORDER BY nombre";
        try (PreparedStatement ps = conn.prepareStatement(any);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getString(1);
        } catch (SQLException ignore) {}
        return null;
    }
}
