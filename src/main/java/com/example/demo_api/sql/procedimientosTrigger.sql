USE ConstruccionDB;
go
CREATE PROCEDURE sp_RegistrarProyectoCompleto
    @IdCliente VARCHAR(50),
    @Titulo VARCHAR(150),
    @Descripcion VARCHAR(MAX),
    @IdAsesor VARCHAR(50) = NULL -- Parámetro opcional (puede ser NULL)
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @NuevoIdServicio VARCHAR(50) = NEWID();
    DECLARE @NuevoIdProyecto VARCHAR(50) = NEWID();

    BEGIN TRANSACTION;
    BEGIN TRY
        -- 1. Crear el Servicio (Con o sin asesor)
        INSERT INTO Servicio (idServicio, idCliente, idAsesor, fechaInicio)
        VALUES (@NuevoIdServicio, @IdCliente, @IdAsesor, GETDATE());

        -- 2. Crear el Proyecto vinculado
        INSERT INTO Proyecto (idProyecto, idServicio, titulo, descripcion, fechaCreacion)
        VALUES (@NuevoIdProyecto, @NuevoIdServicio, @Titulo, @Descripcion, GETDATE());

        COMMIT TRANSACTION;
        SELECT 'Exito' as Estado, @NuevoIdProyecto as IdProyectoGenerado;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        SELECT 'Error' as Estado, ERROR_MESSAGE() as Mensaje;
    END CATCH
END
GO

CREATE PROCEDURE sp_CompararPreciosPorMaterial
    @IdMaterial VARCHAR(50)
AS
BEGIN
    -- Selecciona el material ordenado por precio (Barato -> Caro)
    SELECT 
        c.idCotizacion,
        p.nombre AS NombreProveedor,
        m.nombre AS NombreMaterial,
        c.precioMaterial,
        p.url AS EnlaceCompra
    FROM Cotizacion c
    INNER JOIN Proveedor p ON c.idProveedor = p.idProveedor
    INNER JOIN Material m ON c.idMaterial = m.idMaterial
    WHERE c.idMaterial = @IdMaterial
    ORDER BY c.precioMaterial ASC;
END
GO

CREATE PROCEDURE sp_AgregarMaterialProforma
    @IdProforma VARCHAR(50),
    @IdCotizacion VARCHAR(50),      -- Usamos la cotización seleccionada
    @IdAreaConstruccion VARCHAR(50),
    @Cantidad DECIMAL(18, 2)
AS
BEGIN
    DECLARE @PrecioUnitario DECIMAL(18, 2);
    DECLARE @Subtotal DECIMAL(18, 2);

    -- 1. Obtener precio actual de la cotización
    SELECT @PrecioUnitario = precioMaterial 
    FROM Cotizacion 
    WHERE idCotizacion = @IdCotizacion;

    -- 2. Calcular subtotal
    SET @Subtotal = @PrecioUnitario * @Cantidad;

    -- 3. Insertar detalle
    INSERT INTO MaterialProforma (idProforma, idCotizacion, idAreaConstruccion, cantidad, subtotal)
    VALUES (@IdProforma, @IdCotizacion, @IdAreaConstruccion, @Cantidad, @Subtotal);

    -- 4. Actualizar el total de la cabecera Proforma (Trigger manual)
    UPDATE Proforma
    SET costoTotal = (SELECT SUM(subtotal) FROM MaterialProforma WHERE idProforma = @IdProforma)
    WHERE idProforma = @IdProforma;
END
GO

CREATE TRIGGER trg_ValidarConsistenciaProyecto
ON MaterialProforma
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- Verificamos si existe algún registro en la inserción que viole la regla
    IF EXISTS (
        SELECT 1
        FROM inserted i
        -- 1. Obtenemos el Proyecto dueño de la Proforma
        INNER JOIN Proforma p ON i.idProforma = p.idProforma
        -- 2. Obtenemos el Proyecto dueño del Área de Construcción
        INNER JOIN AreaConstruccion a ON i.idAreaConstruccion = a.idAreaConstruccion
        -- 3. VALIDACIÓN: Si los IDs de proyecto son diferentes, hay error
        WHERE p.idProyecto <> a.idProyecto
    )
    BEGIN
        -- Si entramos aquí, significa que alguien intentó cruzar datos de proyectos distintos
        RAISERROR ('Error de Integridad Lógica: No puedes asignar un material de una Proforma a un Área de Construcción de otro Proyecto diferente.', 16, 1);
        
        -- Cancelamos la operación y deshacemos los cambios
        ROLLBACK TRANSACTION;
    END
END;
GO


