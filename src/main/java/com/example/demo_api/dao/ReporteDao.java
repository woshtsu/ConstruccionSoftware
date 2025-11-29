package com.example.demo_api.dao;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReporteDao {
    private final DataSource dataSource;

    public ReporteDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<com.example.demo_api.dto.ReporteDTO> listarPorPersona(String idPersona) throws SQLException {
        String sql = "SELECT r.idReporte, r.fecha, r.tipoFormato " +
                "FROM Reporte r " +
                "INNER JOIN Proyecto p ON r.idReporte = p.idReporte " +
                "INNER JOIN Servicio s ON p.idServicio = s.idServicio " +
                "WHERE s.idCliente = ? " +
                "ORDER BY r.fecha DESC";
        List<com.example.demo_api.dto.ReporteDTO> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idPersona);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    com.example.demo_api.dto.ReporteDTO dto = new com.example.demo_api.dto.ReporteDTO();
                    dto.setIdReporte(rs.getString("idReporte"));
                    dto.setFecha(rs.getTimestamp("fecha"));
                    dto.setTipoFormato(rs.getString("tipoFormato"));
                    list.add(dto);
                }
            }
        }
        return list;
    }
}
