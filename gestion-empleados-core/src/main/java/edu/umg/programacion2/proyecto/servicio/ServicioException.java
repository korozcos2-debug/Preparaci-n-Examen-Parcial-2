package edu.umg.programacion2.proyecto.servicio;

public class ServicioException extends Exception {

    private static final long serialVersionUID = 1L;

    public ServicioException(String mensaje) {
        super(mensaje);
    }

    public ServicioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}