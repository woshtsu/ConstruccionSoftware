-- =============================================
-- SCRIPT DE DATOS SEMILLA (2 REGISTROS POR TABLA)
-- Ordenado para respetar Integridad Referencial
-- =============================================

USE ConstruccionDB;
GO

-- 1. CATALOGOS INDEPENDIENTES (Categoría y Proveedor)
INSERT INTO Categoria (idCategoria, nombre) VALUES 
('CAT-01', 'Material Seco (Drywall)'),
('CAT-02', 'Ferretería y Fijaciones');

INSERT INTO Proveedor (idProveedor, nombre, url) VALUES 
('PROV-01', 'Sodimac Constructor', 'https://sodimac.falabella.com.pe'),
('PROV-02', 'Maestro Home Center', 'https://www.maestro.com.pe');

-- 2. MATERIALES (Dependen de Categoría)
INSERT INTO Material (idMaterial, idCategoria, nombre, tipoUnidad) VALUES 
('MAT-01', 'CAT-01', 'Plancha de Yeso Standard 1/2"', 'Unidad'),
('MAT-02', 'CAT-02', 'Tornillo Punta Fina 6x1"', 'Caja x 100');

-- 3. COTIZACIONES (Dependen de Material y Proveedor)
-- Esto permite comparar precios
INSERT INTO Cotizacion (idCotizacion, idMaterial, idProveedor, precioMaterial, ultimaFechaActualizada) VALUES 
('COT-01', 'MAT-01', 'PROV-01', 32.50, GETDATE()), -- Plancha en Sodimac
('COT-02', 'MAT-02', 'PROV-02', 15.00, GETDATE()); -- Tornillos en Maestro

-- 4. ACTORES (Personas y Asesores)
INSERT INTO Asesor (idAsesor, nombre, apellidos, telefono, email, password) VALUES 
('ASE-01', 'Jorge', 'Chumpitaz', '999888777', 'jorge@asesor.com', '123456'),
('ASE-02', 'Maria', 'Gomez', '999111222', 'maria@asesor.com', '123456');

-- Insertamos en la tabla padre PERSONA
INSERT INTO Persona (idUsuario, nombre, email, password, contacto, tipoPersona) VALUES 
('USU-01', 'Juan Perez', 'juan@gmail.com', 'pass123', '987654321', 'NATURAL'),
('USU-02', 'Constructora SAC', 'contacto@cons.com', 'pass123', '01234567', 'EMPRESA');

insert into Persona (idUsuario, nombre, email, password, contacto, tipoPersona) VALUES ('ADM-01','admin1','admin@drywallpro.com', 'admin123','00000','NATURAL');

-- Insertamos en las tablas hijas (Herencia)
INSERT INTO PersonaNatural (idUsuario, dni) VALUES ('USU-01', '70123456');
INSERT INTO Empresa (idUsuario, ruc, direccion) VALUES ('USU-02', '20123456789', 'Av. Central 123');

-- 5. SERVICIOS (El núcleo de la lógica de negocio)
-- Caso 1: Usuario Normal (USU-01) SIN asesor (NULL) -> Autoconstrucción
INSERT INTO Servicio (idServicio, idCliente, idAsesor, fechaInicio) VALUES 
('SERV-01', 'USU-01', NULL, GETDATE());

-- Caso 2: Empresa (USU-02) CON asesor (ASE-01) -> Asesoría Experta
INSERT INTO Servicio (idServicio, idCliente, idAsesor, fechaInicio) VALUES 
('SERV-02', 'USU-02', 'ASE-01', GETDATE());

-- 6. REPORTES (Previos al proyecto o generados vacíos)
INSERT INTO Reporte (idReporte, tipoFormato, contenido) VALUES 
('REP-01', 'PDF', NULL),
('REP-02', 'EXCEL', NULL);

-- 7. PROYECTOS (Dependen de Servicio y Reporte)
INSERT INTO Proyecto (idProyecto, idServicio, idReporte, titulo, descripcion, fechaCreacion) VALUES 
('PROY-01', 'SERV-01', 'REP-01', 'Remodelación Sala Juan', 'Pared divisoria drywall', GETDATE()),
('PROY-02', 'SERV-02', 'REP-02', 'Oficinas Administrativas', 'Cielo raso y divisiones', GETDATE());

-- 8. AREAS DE CONSTRUCCION (Dependen de Proyecto)
-- IMPORTANTE: Aquí definimos que AREA-01 es de PROY-01 y AREA-02 es de PROY-02
INSERT INTO AreaConstruccion (idAreaConstruccion, idProyecto, tipo, largo, alto, espesor, superficie) VALUES 
('AREA-01', 'PROY-01', 'Muro Divisorio', 5.00, 2.50, 0.12, 12.50), -- Del Proyecto 1
('AREA-02', 'PROY-02', 'Cielo Raso', 10.00, 10.00, 0.01, 100.00);  -- Del Proyecto 2

-- 9. PROFORMAS (Dependen de Proyecto)
-- IMPORTANTE: Aquí definimos que PROF-01 es de PROY-01 y PROF-02 es de PROY-02
INSERT INTO Proforma (idProforma, idProyecto, fecha, costoTotal) VALUES 
('PROF-01', 'PROY-01', GETDATE(), 0.00), -- Del Proyecto 1
('PROF-02', 'PROY-02', GETDATE(), 0.00); -- Del Proyecto 2

-- 10. MATERIALES DE LA PROFORMA (LA PRUEBA DE FUEGO)
-- Dependen de: Proforma, Cotizacion y AreaConstruccion.
-- EL TRIGGER VALIDARÁ QUE LA PROFORMA Y EL AREA SEAN DEL MISMO PROYECTO.

-- Registro 1: Correcto (Todo pertenece a PROY-01)
-- Proforma PROF-01 (es de PROY-01) + Area AREA-01 (es de PROY-01)
INSERT INTO MaterialProforma (idMaterialProforma, idProforma, idCotizacion, idAreaConstruccion, cantidad, subtotal) VALUES 
('MP-01', 'PROF-01', 'COT-01', 'AREA-01', 10, 325.00); 

-- Registro 2: Correcto (Todo pertenece a PROY-02)
-- Proforma PROF-02 (es de PROY-02) + Area AREA-02 (es de PROY-02)
INSERT INTO MaterialProforma (idMaterialProforma, idProforma, idCotizacion, idAreaConstruccion, cantidad, subtotal) VALUES 
('MP-02', 'PROF-02', 'COT-02', 'AREA-02', 50, 750.00); 

-- Actualizamos los totales en las proformas (opcional, para consistencia)
UPDATE Proforma SET costoTotal = 325.00 WHERE idProforma = 'PROF-01';
UPDATE Proforma SET costoTotal = 750.00 WHERE idProforma = 'PROF-02';

GO

INSERT INTO MaterialProforma (idMaterialProforma, idProforma, idCotizacion, idAreaConstruccion, cantidad, subtotal) 
VALUES (NEWID(), 'PROF-01'	, 'COT-01', 'AREA-02', 1, 100);