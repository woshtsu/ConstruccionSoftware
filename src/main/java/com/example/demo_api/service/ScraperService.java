package com.example.demo_api.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class ScraperService {
    private static final String UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
    private static final int TIMEOUT_MS = 7000;

    private final com.example.demo_api.dao.MaterialDao materialDao;

    public ScraperService(com.example.demo_api.dao.MaterialDao materialDao) {
        this.materialDao = materialDao;
    }

    public Document obtenerDocumento(String url) throws Exception {
        return Jsoup.connect(url)
                .userAgent(UA)
                .timeout(TIMEOUT_MS)
                .get();
    }

    public BigDecimal extraerPrecioSeguro(Document doc) {
        Element precioElement = doc.selectFirst("[id^=testId-pod-prices]");
        if (precioElement == null) {
            Elements candidatos = doc.select("li[class^=prices-main-price] span");
            if (!candidatos.isEmpty()) {
                precioElement = candidatos.first();
            }
        }
        if (precioElement == null) {
            Elements candidatos = doc.select("span");
            for (Element candidato : candidatos) {
                String parentClass = candidato.parent() != null ? candidato.parent().attr("class") : "";
                if (parentClass.contains("btr-total")) {
                    continue;
                }
                String cls = candidato.className();
                if (cls.contains("main-price") || cls.contains("copy10")) {
                    precioElement = candidato;
                    break;
                }
            }
        }
        if (precioElement != null) {
            String texto = precioElement.text();
            BigDecimal valor = limpiarPrecio(texto);
            if (valor != null) return valor;
        }
        return null;
    }

    public BigDecimal limpiarPrecio(String texto) {
        if (texto == null) return null;
        String t = texto.replace("\u00A0", " ").trim();
        t = t.replace("S/", "").replace("S$", "").replace("$", "");
        t = t.replaceAll("[^0-9,\\.]", "").trim();
        if (t.isEmpty()) return null;
        if (t.contains(",") && t.contains(".")) {
            t = t.replace(",", "");
        } else if (t.contains(",")) {
            t = t.replace(",", ".");
        }
        try {
            return new BigDecimal(t);
        } catch (Exception e) {
            return null;
        }
    }

    public java.util.List<com.example.demo_api.dto.ProductoListadoDTO> buscarListadoFalabella(String query) throws Exception {
        String q = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = "https://www.falabella.com.pe/falabella-pe/search?Ntt=" + q;
        Document doc = obtenerDocumento(url);
        java.util.List<com.example.demo_api.dto.ProductoListadoDTO> list = new java.util.ArrayList<>();
        Elements titulos = doc.select("b[id^=testId-pod-displaySubTitle]");
        for (Element t : titulos) {
            com.example.demo_api.dto.ProductoListadoDTO dto = new com.example.demo_api.dto.ProductoListadoDTO();
            dto.setTitulo(t.text());
            Element container = t.parent();
            Element priceEl = null;
            for (int i = 0; i < 4 && container != null && priceEl == null; i++) {
                priceEl = container.selectFirst("[id^=testId-pod-prices]");
                container = container.parent();
            }
            if (priceEl == null) {
                priceEl = doc.selectFirst("[id^=testId-pod-prices]");
            }
            BigDecimal p = priceEl != null ? limpiarPrecio(priceEl.text()) : null;
            dto.setPrecio(p);
            Element a = t.closest("a");
            if (a == null) {
                a = t.parent() != null ? t.parent().selectFirst("a[href]") : null;
            }
            if (a == null) {
                Element container2 = t.parent();
                for (int i = 0; i < 6 && container2 != null && a == null; i++) {
                    a = container2.selectFirst("a[href]");
                    container2 = container2.parent();
                }
            }
            if (a == null) {
                a = doc.selectFirst("a[href*=/product/]");
            }
            String enlace = a != null ? a.absUrl("href") : null;
            dto.setUrl(enlace);
            if (dto.getTitulo() != null && !dto.getTitulo().isBlank()) {
                try {
                    String idMat = materialDao.registrarSiNoExiste(dto.getTitulo());
                    dto.setIdMaterial(idMat);
                } catch (Exception ignore) {}
            }
            list.add(dto);
        }
        return list;
    }
}
