package com.example.demo_api.dao;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class ProyectoDao {
    private final DataSource dataSource;

    public ProyectoDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String registrarProyectoCompleto(String idCliente, String titulo, String descripcion, String idAsesor) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement cs = conn.prepareCall("{call sp_RegistrarProyectoCompleto(?, ?, ?, ?)}")) {
            cs.setString(1, idCliente);
            cs.setString(2, titulo);
            cs.setString(3, descripcion);
            if (idAsesor == null || idAsesor.isBlank()) {
                cs.setNull(4, Types.VARCHAR);
            } else {
                cs.setString(4, idAsesor);
            }
            boolean hasResult = cs.execute();
            if (hasResult) {
                try (ResultSet rs = cs.getResultSet()) {
                    if (rs.next()) {
                        String estado = rs.getString("Estado");
                        if (estado != null && estado.equalsIgnoreCase("Exito")) {
                            return rs.getString("IdProyectoGenerado");
                        }
                        String mensaje = rs.getString("Mensaje");
                        throw new SQLException(mensaje != null ? mensaje : "Error en procedimiento");
                    }
                }
            }
            throw new SQLException("Sin resultado del procedimiento");
        }
    }

    public com.example.demo_api.dto.ProyectoDTO obtenerPorId(String idProyecto) throws SQLException {
        String sql = "SELECT idProyecto, idServicio, idReporte, titulo, descripcion, fechaCreacion FROM Proyecto WHERE idProyecto = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idProyecto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    com.example.demo_api.dto.ProyectoDTO dto = new com.example.demo_api.dto.ProyectoDTO();
                    dto.setIdProyecto(rs.getString("idProyecto"));
                    dto.setIdServicio(rs.getString("idServicio"));
                    dto.setIdReporte(rs.getString("idReporte"));
                    dto.setTitulo(rs.getString("titulo"));
                    dto.setDescripcion(rs.getString("descripcion"));
                    dto.setFechaCreacion(rs.getTimestamp("fechaCreacion"));
                    return dto;
                }
            }
        }
        return null;
    }
}
