package com.example.demo_api.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CotizacionUtil {
    public static List<Map<String, Object>> listarCotizacionesMock(String idMaterial) {
        return List.of(
                Map.of(
                        "idCotizacion", "COT-MOCK-01",
                        "proveedor", "Sodimac Constructor",
                        "material", idMaterial,
                        "precioMaterial", new BigDecimal("32.50"),
                        "url", "https://sodimac.falabella.com.pe"
                ),
                Map.of(
                        "idCotizacion", "COT-MOCK-02",
                        "proveedor", "Maestro Home Center",
                        "material", idMaterial,
                        "precioMaterial", new BigDecimal("31.90"),
                        "url", "https://www.maestro.com.pe"
                )
        );
    }
}
