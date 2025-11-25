# Contexto del Proyecto: Software de Gestión de Materiales (Drywall)

## 1. Descripción
Software Java Web (JSP/Servlet) para calcular materiales de construcción en seco (Drywall), comparar precios mediante web scraping y generar proformas.

## 2. Stack Tecnológico
- **Backend:** Java (JDK 17+), Servlets, JSP.
- **Base de Datos:** Microsoft SQL Server (T-SQL).
- **Librerías Clave:** Jsoup (Scraping), JDBC (Conexión BD), iText (Reportes PDF - pendiente).
- **Gestor:** Maven.

## 3. Reglas de Negocio Críticas
1. **Lógica de Servicio:** Un `Proyecto` pertenece a un `Servicio`.
   - Si `Servicio.idAsesor` es NULL -> El usuario hace autoconstrucción.
   - Si `Servicio.idAsesor` tiene valor -> El usuario contrató un experto.
2. **Comparación de Precios:** No guardamos precios en `Material`. Usamos una tabla intermedia `Cotizacion` (Material + Proveedor + Precio).
3. **Validación de Integridad:** Existe un Trigger en BD que impide asignar materiales de una Proforma a un Área de Construcción de un proyecto diferente.

## 4. Estructura de Base de Datos
(Ver archivo: /sql/schema.sql)
- Tablas Principales: Proyecto, AreaConstruccion, MaterialProforma.
- Tablas de Soporte: Cotizacion, Proveedor, Material.

## 5. Estilo de Código
- Usar **DAO Pattern** para acceso a datos.
- Usar **Procedimientos Almacenados** para lógica compleja (Crear proyecto, Comparar precios).
- Manejo de excepciones explícito en el Service Layer.

## 5. Estilo de Código
- **Acceso a Datos:** Usar JDBC Puro (java.sql) con `try-with-resources`.
- **Conexión:** Usar clase utilitaria `ConexionDB` (Singleton).
- **Queries:** SIEMPRE usar `PreparedStatement` o `CallableStatement` (para SPs). NUNCA concatenar Strings (evitar SQL Injection).