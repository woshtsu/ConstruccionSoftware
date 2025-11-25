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

    public int actualizarPrecio(String idCotizacion, java.math.BigDecimal precio) throws SQLException {
        String sql = "UPDATE Cotizacion SET precioMaterial = ?, ultimaFechaActualizada = GETDATE() WHERE idCotizacion = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, precio);
            ps.setString(2, idCotizacion);
            return ps.executeUpdate();
        }
    }
}
