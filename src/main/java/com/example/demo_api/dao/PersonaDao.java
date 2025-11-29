package com.example.demo_api.dao;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonaDao {
    private final DataSource dataSource;

    public PersonaDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<com.example.demo_api.dto.PersonaDTO> listarTodas() throws SQLException {
        String sql = "SELECT idUsuario, nombre, email, contacto, tipoPersona FROM Persona ORDER BY nombre";
        List<com.example.demo_api.dto.PersonaDTO> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    com.example.demo_api.dto.PersonaDTO dto = new com.example.demo_api.dto.PersonaDTO();
                    dto.setIdUsuario(rs.getString("idUsuario"));
                    dto.setNombre(rs.getString("nombre"));
                    dto.setEmail(rs.getString("email"));
                    dto.setContacto(rs.getString("contacto"));
                    dto.setTipoPersona(rs.getString("tipoPersona"));
                    list.add(dto);
                }
            }
        }
        return list;
    }
}
