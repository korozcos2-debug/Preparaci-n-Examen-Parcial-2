package edu.umg.programacion2.proyecto.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import edu.umg.programacion2.proyecto.modelo.Empleado;
import edu.umg.programacion2.proyecto.servicio.EmpleadoServicio;
import edu.umg.programacion2.proyecto.servicio.ServicioException;
import edu.umg.programacion2.proyecto.validacion.ValidadorEmpleado;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    private final EmpleadoServicio servicio = new EmpleadoServicio();
    private List<Empleado> empleados = new ArrayList<>();
    private int idSeleccionado = 0;

    private JPanel contentPane;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtCorreo;
    private JTextField txtNombre;
    private JTextField txtDepartamento;
    private JTextField txtSalario;
    private JTextField txtFecha;
    private JCheckBox chkActivo;
    private JButton btnRegistrar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    public VentanaPrincipal() {
        setTitle("Gestión de empleados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 580);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // ---- Tabla (centro) ----
        modelo = new DefaultTableModel(
                new Object[] { "ID", "Correo", "Nombre", "Departamento", "Salario", "Contratación", "Activo" }, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarSeleccionEnFormulario();
            }
        });
        JScrollPane scroll = new JScrollPane(tabla);
        contentPane.add(scroll, BorderLayout.CENTER);

        // ---- Zona inferior: formulario + botones ----
        JPanel panelSur = new JPanel();
        panelSur.setLayout(new BorderLayout(0, 10));
        contentPane.add(panelSur, BorderLayout.SOUTH);

        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridLayout(6, 2, 8, 8));
        panelSur.add(panelForm, BorderLayout.CENTER);

        panelForm.add(new JLabel("Correo electrónico:"));
        txtCorreo = new JTextField();
        panelForm.add(txtCorreo);

        panelForm.add(new JLabel("Nombre completo:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Departamento:"));
        txtDepartamento = new JTextField();
        panelForm.add(txtDepartamento);

        panelForm.add(new JLabel("Salario mensual:"));
        txtSalario = new JTextField();
        panelForm.add(txtSalario);

        panelForm.add(new JLabel("Fecha de contratación (AAAA-MM-DD):"));
        txtFecha = new JTextField();
        panelForm.add(txtFecha);

        panelForm.add(new JLabel("Activo:"));
        chkActivo = new JCheckBox("Sí");
        chkActivo.setSelected(true);
        panelForm.add(chkActivo);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelSur.add(panelBotones, BorderLayout.SOUTH);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrar());
        panelBotones.add(btnRegistrar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> actualizar());
        panelBotones.add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminar());
        panelBotones.add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        panelBotones.add(btnLimpiar);
    }

    /** Se llama al abrir la ventana en tiempo de ejecución (no en el diseñador). */
    public void iniciar() {
        limpiarFormulario();
        cargarTabla();
    }

    // ================= Acciones =================

    private void registrar() {
        Empleado nuevo = leerFormulario();
        if (nuevo == null) {
            return;
        }
        try {
            servicio.registrar(nuevo);
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Empleado registrado.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (ServicioException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void actualizar() {
        if (idSeleccionado == 0) {
            mostrarAviso("Seleccione un empleado de la tabla para actualizarlo.");
            return;
        }
        Empleado editado = leerFormulario();
        if (editado == null) {
            return;
        }
        editado.setId(idSeleccionado);
        try {
            if (servicio.actualizar(editado)) {
                cargarTabla();
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "Empleado actualizado.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                mostrarAviso("El empleado ya no existe en la base de datos.");
                cargarTabla();
            }
        } catch (ServicioException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void eliminar() {
        if (idSeleccionado == 0) {
            mostrarAviso("Seleccione un empleado de la tabla para eliminarlo.");
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Eliminar definitivamente a " + txtNombre.getText() + "?\nEsta acción no se puede deshacer.",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            if (servicio.eliminar(idSeleccionado)) {
                cargarTabla();
                limpiarFormulario();
            } else {
                mostrarAviso("El empleado ya no existe en la base de datos.");
                cargarTabla();
            }
        } catch (ServicioException ex) {
            mostrarError(ex.getMessage());
        }
    }

    // ================= Apoyo =================

    private void cargarTabla() {
        try {
            empleados = servicio.listar();
        } catch (ServicioException ex) {
            mostrarError(ex.getMessage());
            return;
        }
        modelo.setRowCount(0);
        for (Empleado e : empleados) {
            modelo.addRow(new Object[] {
                    e.getId(),
                    e.getCorreo(),
                    e.getNombre(),
                    e.getDepartamento(),
                    "Q" + e.getSalario().toPlainString(),
                    e.getFechaContratacion(),
                    e.isActivo() ? "Activo" : "Inactivo" });
        }
    }

    private void cargarSeleccionEnFormulario() {
        int fila = tabla.getSelectedRow();
        if (fila < 0 || fila >= empleados.size()) {
            return;
        }
        Empleado e = empleados.get(fila);
        idSeleccionado = e.getId();
        txtCorreo.setText(e.getCorreo());
        txtNombre.setText(e.getNombre());
        txtDepartamento.setText(e.getDepartamento());
        txtSalario.setText(e.getSalario().toPlainString());
        txtFecha.setText(e.getFechaContratacion().toString());
        chkActivo.setSelected(e.isActivo());
    }

    /** Lee el formulario; devuelve null (y avisa) si algún campo tiene formato inválido. */
    private Empleado leerFormulario() {
        BigDecimal salario = ValidadorEmpleado.parsearSalario(txtSalario.getText());
        if (salario == null) {
            mostrarAviso("Salario inválido. Escriba un número, por ejemplo 8500.00");
            return null;
        }
        LocalDate fecha = ValidadorEmpleado.parsearFecha(txtFecha.getText());
        if (fecha == null) {
            mostrarAviso("Fecha inválida. Use el formato AAAA-MM-DD, por ejemplo 2024-03-15");
            return null;
        }
        Empleado e = new Empleado(txtCorreo.getText().trim(), txtNombre.getText().trim(),
                txtDepartamento.getText().trim(), salario, fecha, chkActivo.isSelected());

        // Las reglas de negocio se validan aquí para avisar antes de tocar la BD.
        String error = ValidadorEmpleado.validar(e);
        if (error != null) {
            mostrarAviso(error);
            return null;
        }
        return e;
    }

    private void limpiarFormulario() {
        tabla.clearSelection();
        idSeleccionado = 0;
        txtCorreo.setText("");
        txtNombre.setText("");
        txtDepartamento.setText("");
        txtSalario.setText("");
        txtFecha.setText(LocalDate.now().toString());
        chkActivo.setSelected(true);
        txtCorreo.requestFocus();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarAviso(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Datos inválidos", JOptionPane.WARNING_MESSAGE);
    }
}