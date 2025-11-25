-- Crear la Base de Datos
CREATE DATABASE ConstruccionDB;
GO
USE ConstruccionDB;
GO

-- 1. Tabla Padre: Usuario / Persona
-- Nota: Aquí unimos atributos comunes. El "login" se maneja con email/password.
CREATE TABLE Persona (
    idUsuario VARCHAR(50) PRIMARY KEY DEFAULT NEWID(), -- Usamos GUIDs automáticos
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, -- Para guardar hash
    contacto VARCHAR(20),
    tipoPersona VARCHAR(20) CHECK (tipoPersona IN ('NATURAL', 'EMPRESA')) -- Discriminador
);

-- 2. Tablas Hijas (Herencia)
CREATE TABLE PersonaNatural (
    idUsuario VARCHAR(50) PRIMARY KEY,
    dni VARCHAR(8) NOT NULL UNIQUE,
    FOREIGN KEY (idUsuario) REFERENCES Persona(idUsuario)
);

CREATE TABLE Empresa (
    idUsuario VARCHAR(50) PRIMARY KEY,
    ruc VARCHAR(11) NOT NULL UNIQUE,
    direccion VARCHAR(200),
    FOREIGN KEY (idUsuario) REFERENCES Persona(idUsuario)
);

-- 3. Tabla Asesor (Entidad separada según tu diagrama)
CREATE TABLE Asesor (
    idAsesor VARCHAR(50) PRIMARY KEY DEFAULT NEWID(),
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    password VARCHAR(255)
);

-- 4. Tabla Servicio (Contenedor de la lógica de Asesoría)
-- Aquí aplicamos tu lógica: si idAsesor es NULL, es autoconstrucción.
CREATE TABLE Servicio (
    idServicio VARCHAR(50) PRIMARY KEY DEFAULT NEWID(),
    idCliente VARCHAR(50) NOT NULL,
    idAsesor VARCHAR(50) NULL, -- PUEDE SER NULL (Tu requerimiento clave)
    fechaInicio DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (idCliente) REFERENCES Persona(idUsuario),
    FOREIGN KEY (idAsesor) REFERENCES Asesor(idAsesor)
);

-- 5. Tabla Reporte (Debe existir antes de Proyecto si es FK, o al revés si es 1 a *)
-- Según diagrama: Proyecto tiene 1 reporte. Lo vinculamos simple.
CREATE TABLE Reporte (
    idReporte VARCHAR(50) PRIMARY KEY DEFAULT NEWID(),
    fecha DATETIME DEFAULT GETDATE(),
    contenido XML, -- O VARCHAR(MAX) para guardar el resultado serializado
    tipoFormato VARCHAR(10) -- PDF/EXCEL
);

-- 6. Tabla Proyecto
CREATE TABLE Proyecto (
    idProyecto VARCHAR(50) PRIMARY KEY DEFAULT NEWID(),
    idServicio VARCHAR(50) NOT NULL,
    idReporte VARCHAR(50) NULL, -- Se llena cuando se genera
    titulo VARCHAR(150) NOT NULL,
    descripcion VARCHAR(MAX),
    fechaCreacion DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (idServicio) REFERENCES Servicio(idServicio),
    FOREIGN KEY (idReporte) REFERENCES Reporte(idReporte)
);

-- 7. Tabla AreaConstruccion
CREATE TABLE AreaConstruccion (
    idAreaConstruccion VARCHAR(50) PRIMARY KEY DEFAULT NEWID(),
    idProyecto VARCHAR(50) NOT NULL,
    tipo VARCHAR(50) NOT NULL, -- Pared, Techo, etc.
    largo DECIMAL(10, 2) NOT NULL,
    alto DECIMAL(10, 2) NOT NULL,
    espesor DECIMAL(10, 2) NOT NULL,
    superficie DECIMAL(10, 2) NOT NULL, -- Se puede calcular, pero se guarda por rendimiento
    archivoImportado VARCHAR(255), -- Ruta del archivo
    FOREIGN KEY (idProyecto) REFERENCES Proyecto(idProyecto)
);

-- 8. Catálogo y Proveedores
CREATE TABLE Categoria (
    idCategoria VARCHAR(50) PRIMARY KEY DEFAULT NEWID(),
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE Material (
    idMaterial VARCHAR(50) PRIMARY KEY DEFAULT NEWID(),
    idCategoria VARCHAR(50) NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    tipoUnidad VARCHAR(20), -- Kg, Unidad, Metro
    FOREIGN KEY (idCategoria) REFERENCES Categoria(idCategoria)
);

CREATE TABLE Proveedor (
    idProveedor VARCHAR(50) PRIMARY KEY DEFAULT NEWID(),
    nombre VARCHAR(100) NOT NULL,
    url VARCHAR(255)
);

-- 9. Tabla Cotizacion (La relación Muchos a Muchos con Precio)
CREATE TABLE Cotizacion (
    idCotizacion VARCHAR(50) PRIMARY KEY DEFAULT NEWID(),
    idMaterial VARCHAR(50) NOT NULL,
    idProveedor VARCHAR(50) NOT NULL,
    precioMaterial DECIMAL(18, 2) NOT NULL,
    ultimaFechaActualizada DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (idMaterial) REFERENCES Material(idMaterial),
    FOREIGN KEY (idProveedor) REFERENCES Proveedor(idProveedor)
);

-- 10. Proforma (Historial de presupuestos)
CREATE TABLE Proforma (
    idProforma VARCHAR(50) PRIMARY KEY DEFAULT NEWID(),
    idProyecto VARCHAR(50) NOT NULL,
    fecha DATETIME DEFAULT GETDATE(),
    costoTotal DECIMAL(18, 2) DEFAULT 0,
    FOREIGN KEY (idProyecto) REFERENCES Proyecto(idProyecto)
);

-- 11. MaterialProforma (Detalle de la proforma)
-- Vincula la Cotización exacta (Precio + Proveedor) con el Área específica
CREATE TABLE MaterialProforma (
    idMaterialProforma VARCHAR(50) PRIMARY KEY DEFAULT NEWID(),
    idProforma VARCHAR(50) NOT NULL,
    idCotizacion VARCHAR(50) NOT NULL, -- FK a la cotización (NO solo material)
    idAreaConstruccion VARCHAR(50) NULL, -- Opcional: saber para qué pared es el material
    cantidad DECIMAL(18, 2) NOT NULL,
    subtotal DECIMAL(18, 2) NOT NULL,
    FOREIGN KEY (idProforma) REFERENCES Proforma(idProforma),
    FOREIGN KEY (idCotizacion) REFERENCES Cotizacion(idCotizacion),
    FOREIGN KEY (idAreaConstruccion) REFERENCES AreaConstruccion(idAreaConstruccion)
);
GO

