package edu.umg.programacion2.proyecto.servicio;

import java.sql.SQLException;
import java.util.List;

import edu.umg.programacion2.proyecto.dao.EmpleadoDAO;
import edu.umg.programacion2.proyecto.modelo.Empleado;
import edu.umg.programacion2.proyecto.validacion.ValidadorEmpleado;

/**
 * Punto de entrada de la UI: valida, llama al DAO y traduce SQLException
 * a ServicioException con mensajes legibles (sin stacktrace).
 */
public class EmpleadoServicio {

    private final EmpleadoDAO dao = new EmpleadoDAO();

    public List<Empleado> listar() throws ServicioException {
        try {
            return dao.listarTodos();
        } catch (SQLException ex) {
            throw errorBD("No se pudo obtener el listado de empleados.", ex);
        }
    }

    public Empleado registrar(Empleado e) throws ServicioException {
        validar(e);
        try {
            return dao.crear(e);
        } catch (SQLException ex) {
            throw errorBD("No se pudo registrar el empleado.", ex);
        }
    }

    public boolean actualizar(Empleado e) throws ServicioException {
        validar(e);
        try {
            return dao.actualizar(e);
        } catch (SQLException ex) {
            throw errorBD("No se pudo actualizar el empleado.", ex);
        }
    }

    public boolean eliminar(int id) throws ServicioException {
        try {
            return dao.eliminar(id);
        } catch (SQLException ex) {
            throw errorBD("No se pudo eliminar el empleado.", ex);
        }
    }

    private void validar(Empleado e) throws ServicioException {
        String error = ValidadorEmpleado.validar(e);
        if (error != null) {
            throw new ServicioException(error);
        }
    }

    private ServicioException errorBD(String mensaje, SQLException ex) {
        // El detalle técnico va a la consola del programador, no al usuario.
        System.err.println(mensaje + " Detalle: " + ex.getMessage());
        return new ServicioException(mensaje + " Verifique la conexión con la base de datos.", ex);
    }
}