import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RuletaGUI extends JFrame {
    private Ruleta ruleta;
    private Jugador jugador;

    private JButton girarButton;
    private JTextField montoApuestaField;
    private JComboBox<String> tipoApuestaComboBox;
    private JTextField numeroApuestaField;
    private JComboBox<String> colorApuestaComboBox;
    private JLabel saldoLabel;
    private JLabel resultadoLabel;
    private JLabel casillaSalidaLabel; // Nueva etiqueta para mostrar la casilla

    public RuletaGUI() {
        ruleta = new Ruleta();
        jugador = new Jugador("Nombre del Jugador", 25, 1000.0);

        setTitle("Ruleta Casino");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeComponents();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        girarButton = new JButton("Girar la Ruleta");
        montoApuestaField = new JTextField(10);
        saldoLabel = new JLabel("Saldo: $" + jugador.getSaldo());
        resultadoLabel = new JLabel("Resultado: ");
        casillaSalidaLabel = new JLabel("Casilla de Salida: "); // Etiqueta para mostrar la casilla

        tipoApuestaComboBox = new JComboBox<>();
        tipoApuestaComboBox.addItem("Apuesta Por Número");
        tipoApuestaComboBox.addItem("Apuesta Por Color");
        tipoApuestaComboBox.addItem("Apuesta Por Docena");
        tipoApuestaComboBox.addItem("Apuesta Faltas");
        tipoApuestaComboBox.addItem("Apuesta Pasa");

        tipoApuestaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) tipoApuestaComboBox.getSelectedItem();
                montoApuestaField.setText("");
                numeroApuestaField.setText("");
                colorApuestaComboBox.setSelectedIndex(0);
                if (seleccion.equals("Apuesta Por Número")) {
                    numeroApuestaField.setEnabled(true);
                    colorApuestaComboBox.setEnabled(false);
                } else if (seleccion.equals("Apuesta Por Color")) {
                    numeroApuestaField.setEnabled(false);
                    colorApuestaComboBox.setEnabled(true);
                } else if (seleccion.equals("Apuesta Por Docena")) {
                    numeroApuestaField.setEnabled(true);
                    colorApuestaComboBox.setEnabled(false);
                } else {
                    numeroApuestaField.setEnabled(false);
                    colorApuestaComboBox.setEnabled(false);
                }
            }
        });

        girarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String montoApuestaStr = montoApuestaField.getText();
                String tipoApuesta = (String) tipoApuestaComboBox.getSelectedItem();
                boolean camposValidos = true;

                // Verificar si el campo de monto está vacío
                if (montoApuestaStr.isEmpty()) {
                    camposValidos = false;
                    JOptionPane.showMessageDialog(null, "Ingrese el monto de la apuesta", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Verificar campos adicionales según el tipo de apuesta
                if (tipoApuesta.equals("Apuesta Por Número")) {
                    String numeroApuestaStr = numeroApuestaField.getText();
                    if (numeroApuestaStr.isEmpty()) {
                        camposValidos = false;
                        JOptionPane.showMessageDialog(null, "Ingrese un número para la apuesta", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (tipoApuesta.equals("Apuesta Por Docena")) {
                    String numeroApuestaStr = numeroApuestaField.getText();
                    if (numeroApuestaStr.isEmpty()) {
                        camposValidos = false;
                        JOptionPane.showMessageDialog(null, "Ingrese un número de docena (1-3) para la apuesta", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int docenaElegida = Integer.parseInt(numeroApuestaStr);
                        if (docenaElegida < 1 || docenaElegida > 3) {
                            camposValidos = false;
                            JOptionPane.showMessageDialog(null, "Número de docena no válido (debe ser 1, 2 o 3)", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (tipoApuesta.equals("Apuesta Por Color")) {
                    String colorElegido = (String) colorApuestaComboBox.getSelectedItem();
                    if (colorElegido == null || colorElegido.isEmpty()) {
                        camposValidos = false;
                        JOptionPane.showMessageDialog(null, "Seleccione un color para la apuesta", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (camposValidos) {
                    double montoApuesta = Double.parseDouble(montoApuestaStr);
                    Casilla resultado = ruleta.girar();
                    boolean esGanadora = jugadorRealizaApuesta(tipoApuesta, montoApuesta, resultado);

                    resultadoLabel.setText("Resultado: " + (esGanadora ? "Ganaste" : "Perdiste"));
                    saldoLabel.setText("Saldo: $" + jugador.getSaldo());
                    casillaSalidaLabel.setText("Casilla de Salida: " + resultado.toString());
                }
            }
        });

        numeroApuestaField = new JTextField(5);
        numeroApuestaField.setEnabled(false);

        colorApuestaComboBox = new JComboBox<>();
        colorApuestaComboBox.addItem("Rojo");
        colorApuestaComboBox.addItem("Negro");
        colorApuestaComboBox.setEnabled(false);

        JPanel panel = new JPanel();
        panel.add(tipoApuestaComboBox);
        panel.add(numeroApuestaField);
        panel.add(colorApuestaComboBox);
        panel.add(montoApuestaField);
        panel.add(girarButton);
        add(panel, BorderLayout.NORTH);
        add(saldoLabel, BorderLayout.WEST);
        add(resultadoLabel, BorderLayout.EAST);
        add(casillaSalidaLabel, BorderLayout.SOUTH); // Agregar la etiqueta de la casilla de salida en la parte inferior
    }

    private boolean jugadorRealizaApuesta(String tipoApuesta, double monto, Casilla resultado) {
        boolean esGanadora = false;

        if (tipoApuesta.equals("Apuesta Por Número")) {
            int numeroElegido = Integer.parseInt(numeroApuestaField.getText());
            Apuesta apuesta = new ApuestaPorNumero(monto, numeroElegido);
            esGanadora = apuesta.esGanadora(resultado);
        } else if (tipoApuesta.equals("Apuesta Por Color")) {
            String colorElegido = (String) colorApuestaComboBox.getSelectedItem();
            Apuesta apuesta = new ApuestaPorColor(monto, colorElegido);
            esGanadora = apuesta.esGanadora(resultado);
        } else if (tipoApuesta.equals("Apuesta Por Docena")) {
            int docenaElegida = Integer.parseInt(numeroApuestaField.getText());
            Apuesta apuesta = new ApuestaPorDocena(monto, docenaElegida);
            esGanadora = apuesta.esGanadora(resultado);
        } else if (tipoApuesta.equals("Apuesta Faltas")) {
            Apuesta apuesta = new ApuestaFalta(monto);
            esGanadora = apuesta.esGanadora(resultado);
        } else if (tipoApuesta.equals("Apuesta Pasa")) {
            Apuesta apuesta = new ApuestaPasa(monto);
            esGanadora = apuesta.esGanadora(resultado);
        }

        if (esGanadora) {
            jugador.ganarApuesta(monto);
        } else {
            jugador.perderApuesta(monto);
        }

        return esGanadora;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RuletaGUI();
        });
    }
}
