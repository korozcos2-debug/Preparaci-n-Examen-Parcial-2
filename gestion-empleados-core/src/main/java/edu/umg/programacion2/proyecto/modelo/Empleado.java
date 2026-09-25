package edu.umg.programacion2.proyecto.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Empleado {
  
    private int id;
    private String correo;
    private String nombre;
    private String departamento;
    private BigDecimal salario;
    private LocalDate fechaContratacion;
    private boolean activo;

    public Empleado() {
    }

    // Para registrar uno nuevo: el id lo asigna la base de datos
    public Empleado(String correo, String nombre, String departamento, BigDecimal salario,
                    LocalDate fechaContratacion, boolean activo) {
        this.correo = correo;
        this.nombre = nombre;
        this.departamento = departamento;
        this.salario = salario;
        this.fechaContratacion = fechaContratacion;
        this.activo = activo;
    }

    // Para empleados leídos de la base de datos (Se corrigió la coma extra en String, nombre)
    public Empleado(int id, String correo, String nombre, String departamento, BigDecimal salario,
                    LocalDate fechaContratacion, boolean activo) {
        this(correo, nombre, departamento, salario, fechaContratacion, activo);
        this.id = id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public BigDecimal getSalario() { return salario; }
    public void setSalario(BigDecimal salario) { this.salario = salario; }

    public LocalDate getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(LocalDate fechaContratacion) { this.fechaContratacion = fechaContratacion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        // Se corrigió la concatenación de las variables en el texto
        return "[" + id + "] " + correo + " - " + nombre + " | " + departamento + " | Q" + salario
                + " | " + (activo ? "Activo" : "Inactivo");
    }
}