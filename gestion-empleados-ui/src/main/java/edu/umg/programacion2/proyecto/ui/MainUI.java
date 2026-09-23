package edu.umg.programacion2.proyecto.ui;

import java.awt.EventQueue;

import edu.umg.programacion2.proyecto.ui.VentanaPrincipal;

public class MainUI {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }
}