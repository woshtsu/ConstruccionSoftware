# API REST - Gestión de Materiales (Drywall)

Breve documentación de la API del backend en Spring Boot para proyectos de construcción en seco. Incluye creación de proyectos, áreas, proformas, detalle de proformas y utilidades de cotización y scraping.

## Base
- Base URL: `http://localhost:8080/api/v1`
- Perfiles:
  - `dev`: conexión local a SQL Server definida en `application-dev.properties`
  - `prod`: variables de entorno (`DB_HOST`, `DB_NAME`, `DB_USER`, `DB_PASS`, `DB_PORT`) en `application-prod.properties`

## Arranque
- Desarrollo: `mvn spring-boot:run`
- Puerto alternativo: `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081`

## Salud
- `GET /hola`
  - Respuesta 200:
    ```json
    { "mensaje": "hola" }
    ```

## Proyectos
- `POST /proyectos`
  - Body:
    ```json
    { "idCliente": "USU-01", "titulo": "Remodelación", "descripcion": "Pared drywall", "idAsesor": null }
    ```
  - Respuesta 201:
    ```json
    { "estado": "Exito", "idProyectoGenerado": "PROY-01", "mensaje": null }
    ```
  - Errores 400: campos requeridos o fallo de SP

- `GET /proyectos/{id}`
  - Respuesta 200:
    ```json
    { "idProyecto": "PROY-01", "idServicio": "SERV-01", "idReporte": null, "titulo": "Remodelación", "descripcion": "Pared drywall", "fechaCreacion": "2025-11-25T14:00:00Z" }
    ```
  - 404 si no existe

- `GET /proyectos/{id}/areas`
  - Respuesta 200 (lista):
    ```json
    [ { "idAreaConstruccion": "AREA-01", "idProyecto": "PROY-01", "tipo": "Muro Divisorio", "largo": 5.0, "alto": 2.5, "espesor": 0.12, "superficie": 12.5, "archivoImportado": null } ]
    ```

- `GET /proyectos/{id}/proformas`
  - Respuesta 200 (lista):
    ```json
    [ { "idProforma": "PROF-01", "idProyecto": "PROY-01", "fecha": "2025-11-25T14:00:00Z", "costoTotal": 0.0 } ]
    ```

## Áreas
- `POST /areas`
  - Body:
    ```json
    { "idProyecto": "PROY-01", "tipo": "Muro Divisorio", "largo": 5.0, "alto": 2.5, "espesor": 0.12, "superficie": 12.5, "archivoImportado": null }
    ```
  - Respuesta 201:
    ```json
    { "estado": "Exito", "idAreaConstruccion": "AREA-01", "mensaje": null }
    ```
  - 400 si campos inválidos

- `GET /areas/{id}`
  - Respuesta 200:
    ```json
    { "idAreaConstruccion": "AREA-01", "idProyecto": "PROY-01", "tipo": "Muro Divisorio", "largo": 5.0, "alto": 2.5, "espesor": 0.12, "superficie": 12.5, "archivoImportado": null }
    ```
  - 404 si no existe

## Proformas
- `POST /proformas`
  - Body:
    ```json
    { "idProyecto": "PROY-01" }
    ```
  - Respuesta 201:
    ```json
    { "estado": "Exito", "idProforma": "PROF-01", "mensaje": null }
    ```
  - 400 si campos inválidos

- `GET /proformas/{id}`
  - Respuesta 200:
    ```json
    { "idProforma": "PROF-01", "idProyecto": "PROY-01", "fecha": "2025-11-25T14:00:00Z", "costoTotal": 250.0 }
    ```
  - 404 si no existe

### Detalles de Proforma
- `POST /proformas/detalles`
  - Body:
    ```json
    { "idProforma": "PROF-01", "idCotizacion": "COT-01", "idAreaConstruccion": "AREA-01", "cantidad": 10 }
    ```
  - Respuesta 201:
    ```json
    { "estado": "Exito", "mensaje": null, "idMaterialProforma": "MP-001", "precioMaterial": 25.5, "costoTotalActualizado": 255.0 }
    ```
  - 400 si campos inválidos o violación de trigger (área/proforma de distinto proyecto)

- `GET /proformas/{id}/detalles`
  - Respuesta 200 (lista):
    ```json
    [ { "idMaterialProforma": "MP-001", "idProforma": "PROF-01", "idCotizacion": "COT-01", "idAreaConstruccion": "AREA-01", "cantidad": 10, "subtotal": 255.0 } ]
    ```

## Cotizaciones
- `GET /materiales/{id}/cotizaciones`
  - Respuesta 200 (ordenado por precio ascendente):
    ```json
    [ { "idCotizacion": "COT-01", "nombreProveedor": "Sodimac Constructor", "nombreMaterial": "Placa Drywall", "precioMaterial": 25.5, "enlaceCompra": "https://..." } ]
    ```

- `PUT /cotizaciones/{id}/precio`
  - Body:
    ```json
    { "url": "https://www.falabella.com.pe/falabella-pe/product/XXXX" }
    ```
  - Respuesta 200:
    ```json
    { "estado": "Exito", "idCotizacion": "COT-01", "precioMaterial": 25.5 }
    ```
  - 400 si no se puede extraer precio o no existe la cotización

## Búsqueda (Scraping)
- `GET /buscar?query=placa+drywall`
  - Respuesta 200 (lista):
    ```json
    [ { "titulo": "Placa Drywall 9mm", "precio": 25.5, "url": "https://www.falabella.com.pe/falabella-pe/product/XXXX" } ]
    ```

## Reglas Clave
- Trigger de integridad en BD: impide cruzar áreas de un proyecto con proformas de otro.
- Snapshot de precio: `MaterialProforma` guarda `idCotizacion` y `subtotal`; si el precio cambia luego en `Cotizacion`, el histórico se mantiene.
- Scraping con Jsoup: evita clases dinámicas; prioriza IDs `testId-*` y precios principales.

## Notas
- Autenticación: no aplicable en esta versión.
- Errores: los endpoints devuelven 400 para validaciones y reglas de negocio, 404 para no encontrado, 201 para creación y 200 para lecturas/actualizaciones.
