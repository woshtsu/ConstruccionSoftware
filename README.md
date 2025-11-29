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

## Guía de Endpoints
- Proyectos
  - Propósito: crear, listar, buscar, obtener y eliminar proyectos
  - Crear: `POST /proyectos`
    - Request:
      ```json
      { "idCliente": "USU-01", "titulo": "Remodelación", "descripcion": "Pared drywall", "idAsesor": null }
      ```
    - Response 201:
      ```json
      { "estado": "Exito", "idProyectoGenerado": "PROY-01" }
      ```
  - Obtener: `GET /proyectos/{id}`
    - Response 200:
      ```json
      { "idProyecto": "PROY-01", "idServicio": "SERV-01", "idReporte": null, "titulo": "Remodelación", "descripcion": "Pared drywall", "fechaCreacion": "2025-11-25T14:00:00Z" }
      ```
  - Listar: `GET /proyectos`
  - Buscar por persona: `GET /proyectos/buscar?nombre=Juan`
  - Eliminar: `DELETE /proyectos/{id}` → 200 `{ "estado": "Exito" }`

- Áreas
  - Propósito: registrar y obtener áreas de construcción
  - Crear: `POST /areas`
    - Request:
      ```json
      { "idProyecto": "PROY-01", "tipo": "Muro Divisorio", "largo": 5.0, "alto": 2.5, "espesor": 0.12, "superficie": 12.5, "archivoImportado": null }
      ```
    - Response 201:
      ```json
      { "estado": "Exito", "idAreaConstruccion": "AREA-01" }
      ```
  - Obtener: `GET /areas/{id}`
  - Listar por proyecto: `GET /proyectos/{id}/areas`

- Proformas
  - Propósito: crear y consultar proformas y sus detalles
  - Crear: `POST /proformas` → 201 `{ "estado": "Exito", "idProforma": "PROF-01" }`
  - Obtener: `GET /proformas/{id}`
  - Agregar detalle: `POST /proformas/detalles`
    - Request:
      ```json
      { "idProforma": "PROF-01", "idCotizacion": "COT-01", "idAreaConstruccion": "AREA-01", "cantidad": 10 }
      ```
    - Response 201:
      ```json
      { "estado": "Exito", "idMaterialProforma": "MP-001", "costoTotalActualizado": 255.0 }
      ```
  - Listar detalles: `GET /proformas/{id}/detalles`
  - Eliminar: `DELETE /proformas/{id}` → 200 `{ "estado": "Exito" }`

- Cotizaciones
  - Propósito: comparar precios, actualizar precio vía scraping y crear cotizaciones
  - Comparar por material: `GET /materiales/{id}/cotizaciones`
  - Actualizar precio: `PUT /cotizaciones/{id}/precio`
    - Request:
      ```json
      { "url": "https://www.falabella.com.pe/falabella-pe/product/XXXX" }
      ```
  - Crear: `POST /cotizaciones`
    - Request:
      ```json
      { "idMaterial": "MAT-01", "nombreProveedor": "Proveedor SA", "nombreMaterial": "Placa Drywall 9mm", "precioMaterial": 25.5, "enlaceCompra": "https://..." }
      ```

- Búsqueda (Scraping)
  - Propósito: buscar materiales en Falabella y registrar en `Material`
  - Buscar: `GET /buscar?query=placa+drywall`
    - Response 200 (lista con `idMaterial`, `titulo`, `precio`, `url`)

- Autenticación
  - Login: `POST /auth/login`
  - Logout: `POST /auth/logout`
  - Uso de token: crear proyecto sin `idCliente` explícito

- Personas
  - Listar: `GET /personas`

- Reportes
  - Listar por persona: `GET /personas/{id}/reportes`

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

### Listado y búsqueda
- `GET /proyectos`
  - Respuesta 200 (lista):
    ```json
    [ { "idProyecto": "PROY-01", "idServicio": "SERV-01", "idReporte": null, "titulo": "Remodelación", "descripcion": "Pared drywall", "fechaCreacion": "2025-11-25T14:00:00Z" } ]
    ```

- `GET /proyectos/buscar?nombre=Juan`
  - Respuesta 200 (lista):
    ```json
    [ { "idProyecto": "PROY-02", "idServicio": "SERV-02", "idReporte": null, "titulo": "División oficina", "descripcion": "Muro drywall", "fechaCreacion": "2025-11-26T10:30:00Z" } ]
    ```
  - Observación: filtra proyectos según `Persona.nombre` asociado al cliente del servicio.

- `DELETE /proyectos/{id}`
  - Respuesta 200:
    ```json
    { "estado": "Exito" }
    ```
  - 404 si no existe
  - Comportamiento: elimina áreas, proformas y sus detalles, luego el proyecto.

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

- `POST /cotizaciones`
  - Body:
    ```json
    { "idMaterial": "MAT-01", "nombreProveedor": "Proveedor SA", "nombreMaterial": "Placa Drywall 9mm", "precioMaterial": 25.5, "enlaceCompra": "https://..." }
    ```
  - Respuesta 201:
    ```json
    { "estado": "Exito", "idCotizacion": "COT-XX" }
    ```
  - 400 si campos inválidos

## Búsqueda (Scraping)
- `GET /buscar?query=placa+drywall`
  - Respuesta 200 (lista):
    ```json
    [ { "idMaterial": "MAT-XX", "titulo": "Placa Drywall 9mm", "precio": 25.5, "url": "https://www.falabella.com.pe/falabella-pe/product/XXXX" } ]
    ```
  - Nota: los materiales encontrados se registran en la tabla `Material` y devuelven `idMaterial` para que el frontend pueda operar (por ejemplo, crear cotizaciones con `idMaterial`).

## Autenticación
- `POST /auth/login`
  - Body:
    ```json
    { "email": "juan@gmail.com", "password": "pass123" }
    ```
  - Respuesta 200:
    ```json
    { "estado": "Exito", "token": "<uuid>", "idCliente": "USU-01" }
    ```
  - 401 si credenciales inválidas

- `POST /auth/logout`
  - Header:
    - `Authorization: Bearer <token>`
  - Respuesta 200:
    ```json
    { "estado": "Exito" }
    ```
  - 400 si token inválido o faltante

### Uso del token
- Crear proyecto con token (sin `idCliente` explícito):
  - Request:
    - Header: `Authorization: Bearer <token>`
    - Body:
      ```json
      { "titulo": "Nuevo Proyecto", "descripcion": "Pared drywall", "idAsesor": null }
      ```
  - Respuesta 201:
    ```json
    { "estado": "Exito", "idProyectoGenerado": "PROY-XX" }
    ```

## Personas
- `GET /personas`
  - Respuesta 200 (lista):
    ```json
    [ { "idUsuario": "USU-01", "nombre": "Juan Pérez", "email": "juan@correo.com", "contacto": "+51 999 999 999", "tipoPersona": "Cliente" } ]
    ```

## Reportes
- `GET /personas/{id}/reportes`
  - Respuesta 200 (lista):
    ```json
    [ { "idReporte": "REP-01", "fecha": "2025-11-27T08:00:00Z", "tipoFormato": "PDF" } ]
    ```
  - Observación: lista reportes asociados a los proyectos del cliente (`Servicio.idCliente = {id}`).

## CORS
- Permitido origen `http://localhost:3000`, métodos `GET, POST, PUT, DELETE, OPTIONS`, headers `*`, credenciales habilitadas.

## Consideraciones de sesión
- Los tokens se almacenan en memoria para desarrollo. Reiniciar el servidor invalida los tokens existentes.

## Reglas Clave
- Trigger de integridad en BD: impide cruzar áreas de un proyecto con proformas de otro.
- Snapshot de precio: `MaterialProforma` guarda `idCotizacion` y `subtotal`; si el precio cambia luego en `Cotizacion`, el histórico se mantiene.
- Scraping con Jsoup: evita clases dinámicas; prioriza IDs `testId-*` y precios principales.

## Notas
- Autenticación: no aplicable en esta versión.
- Errores: los endpoints devuelven 400 para validaciones y reglas de negocio, 404 para no encontrado, 201 para creación y 200 para lecturas/actualizaciones.
-
- `DELETE /proformas/{id}`
  - Respuesta 200:
    ```json
    { "estado": "Exito" }
    ```
  - 404 si no existe
  - Comportamiento: elimina detalles (`MaterialProforma`) y luego la proforma.
