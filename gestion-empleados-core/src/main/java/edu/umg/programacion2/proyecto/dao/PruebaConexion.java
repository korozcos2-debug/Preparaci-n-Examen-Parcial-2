package edu.umg.programacion2.proyecto.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {
    public static void main(String[] args) {
        try (Connection con = ConexionBD.obtenerConexion()) {
            System.out.println("Conexión exitosa a: " + con.getCatalog());
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}