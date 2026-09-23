package edu.umg.programacion2.proyecto.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

import edu.umg.programacion2.proyecto.modelo.Empleado;

public class PruebaEmpleadoDAO {

    public static void main(String[] args) {
        EmpleadoDAO dao = new EmpleadoDAO();

        try {
            System.out.println("--- Listado inicial ---");
            dao.listarTodos().forEach(System.out::println);

            System.out.println("--- Crear ---");
            Empleado nuevo = dao.crear(new Empleado("Prueba Uno", "Ventas",
                    new BigDecimal("4000.00"), LocalDate.of(2024, 3, 15), true));
            System.out.println("Creado con id: " + nuevo.getId());

            System.out.println("--- Buscar ---");
            System.out.println(dao.buscarPorId(nuevo.getId()).orElse(null));

            System.out.println("--- Actualizar ---");
            nuevo.setSalario(new BigDecimal("4500.00"));
            nuevo.setActivo(false);
            System.out.println("Actualizado: " + dao.actualizar(nuevo));
            System.out.println(dao.buscarPorId(nuevo.getId()).orElse(null));

            System.out.println("--- Eliminar ---");
            System.out.println("Eliminado: " + dao.eliminar(nuevo.getId()));
            System.out.println("Existe aún: " + dao.buscarPorId(nuevo.getId()).isPresent());

        } catch (SQLException ex) {
            System.out.println("Error de BD: " + ex.getMessage());
        }
    }
}