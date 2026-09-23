package edu.umg.programacion2.proyecto.validacion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import edu.umg.programacion2.proyecto.modelo.Empleado;

/**
 * Valida las reglas de negocio de un empleado ANTES de llamar al DAO.
 * No conoce Swing ni JDBC.
 */
public class ValidadorEmpleado {

    private static final BigDecimal SALARIO_MAXIMO = new BigDecimal("99999999.99"); // límite de DECIMAL(10,2)

    private ValidadorEmpleado() {
    }

    /** @return mensaje del primer error encontrado, o null si todo es válido. */
    public static String validar(Empleado e) {
        if (e == null) {
            return "No hay datos del empleado.";
        }
        if (e.getNombre() == null || e.getNombre().trim().isEmpty()) {
            return "El nombre no puede estar vacío.";
        }
        if (e.getNombre().trim().length() > 150) {
            return "El nombre no puede superar 150 caracteres.";
        }
        if (e.getDepartamento() == null || e.getDepartamento().trim().isEmpty()) {
            return "El departamento no puede estar vacío.";
        }
        if (e.getDepartamento().trim().length() > 100) {
            return "El departamento no puede superar 100 caracteres.";
        }
        if (e.getSalario() == null || e.getSalario().compareTo(BigDecimal.ZERO) <= 0) {
            return "El salario debe ser mayor a cero.";
        }
        if (e.getSalario().compareTo(SALARIO_MAXIMO) > 0) {
            return "El salario excede el máximo permitido.";
        }
        if (e.getFechaContratacion() == null) {
            return "La fecha de contratación es obligatoria.";
        }
        if (e.getFechaContratacion().isAfter(LocalDate.now())) {
            return "La fecha de contratación no puede ser futura.";
        }
        return null;
    }

    /** @return el valor, o null si el texto no es un número válido. */
    public static BigDecimal parsearSalario(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(texto.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    /** Formato AAAA-MM-DD. @return la fecha, o null si el formato es inválido. */
    public static LocalDate parsearFecha(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(texto.trim());
        } catch (DateTimeParseException ex) {
            return null;
        }
    }
}