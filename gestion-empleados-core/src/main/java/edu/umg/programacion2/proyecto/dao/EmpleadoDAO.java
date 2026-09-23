package edu.umg.programacion2.proyecto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.umg.programacion2.proyecto.modelo.Empleado;

public class EmpleadoDAO {

    public Empleado crear(Empleado e) throws SQLException {
        String sql = "INSERT INTO empleados (nombre, departamento, salario, fecha_contratacion, activo) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getDepartamento());
            ps.setBigDecimal(3, e.getSalario());
            ps.setDate(4, java.sql.Date.valueOf(e.getFechaContratacion()));
            ps.setBoolean(5, e.isActivo());
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        e.setId(rs.getInt(1)); 
                    }
                }
            }
            return e;
        }
    }

    public List<Empleado> listarTodos() throws SQLException {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, departamento, salario, fecha_contratacion, activo FROM empleados";
        
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(mapearEmpleado(rs));
            }
        }
        return lista;
    }

    public Optional<Empleado> buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, departamento, salario, fecha_contratacion, activo FROM empleados WHERE id = ?";
        
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearEmpleado(rs));
                }
            }
        }
        return Optional.empty();
    }

    public boolean actualizar(Empleado e) throws SQLException {
        String sql = "UPDATE empleados SET nombre = ?, departamento = ?, salario = ?, fecha_contratacion = ?, activo = ? WHERE id = ?";
        
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getDepartamento());
            ps.setBigDecimal(3, e.getSalario());
            ps.setDate(4, java.sql.Date.valueOf(e.getFechaContratacion()));
            ps.setBoolean(5, e.isActivo());
            ps.setInt(6, e.getId());
            
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM empleados WHERE id = ?";
        
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
        }
    }

    private Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        Empleado e = new Empleado();
        e.setId(rs.getInt("id"));
        e.setNombre(rs.getString("nombre"));
        e.setDepartamento(rs.getString("departamento"));
        e.setSalario(rs.getBigDecimal("salario"));
        e.setFechaContratacion(rs.getDate("fecha_contratacion").toLocalDate());
        e.setActivo(rs.getBoolean("activo"));
        return e;
    }
}
