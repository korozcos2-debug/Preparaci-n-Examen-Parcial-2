========================================================================
-- Proyecto: Gestión de empleados (Variante A)
-- Autor: Kenedy De Jesus Orozco Salvador
========================================================================
USE empleados_db;
CREATE DATABASE IF NOT EXISTS empleados_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
DROP TABLE IF EXISTS empleados;

--INT>Delega el control de la secuencia numérica a MySQL.
-- Cada vez que insertes un empleado, el motor le asigna automáticamente el número siguiente (+1), evitando colisiones de datos.
--AUTO_INCREMENT: Delega el control de la secuencia numérica a MySQL. Cada vez que insertes un empleado
-- el motor le asigna automáticamente el número siguiente (+1), evitando colisiones de datos.
--PRIMARY KEY: Identifica de forma única e irrepetible a cada registro, indexándolo para que búsquedas por id en el DAO (buscarPorId) tomen milisegundos.


CREATE TABLE empleados (
    id INT AUTO_INCREMENT PRIMARY KEY, 
    nombre VARCHAR(150) NOT NULL,
    departamento VARCHAR(100) NOT NULL,
    salario DECIMAL(10,2) NOT NULL CHECK (salario > 0),
    fecha_contratacion DATE NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO empleados (nombre, departamento, salario, fecha_contratacion, activo) VALUES
    ('Sofía Alvarado', 'Sistemas', 2500.50, '2023-05-12', TRUE),
    ('Carlos Mendoza', 'Recursos Humanos', 1800.00, '2021-11-20', TRUE),
    ('Ana Gómez', 'Contabilidad', 3200.75, '2020-01-15', TRUE),
    ('Luis Martínez', 'Marketing', 1500.00, '2024-02-10', FALSE);

SELECT * FROM empleados;