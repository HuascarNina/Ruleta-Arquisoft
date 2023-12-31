import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.ImageIcon;
import java.net.URL;

public class RuletaGUI extends JFrame {
    private Ruleta ruleta;
    private Jugador jugador;

    private JButton girarButton;
    private JButton verEstadisticasButton;
    private JTextField montoApuestaField;
    private JComboBox<String> tipoApuestaComboBox;
    private JTextField numeroApuestaField;
    private JComboBox<String> colorApuestaComboBox;
    private JLabel saldoLabel;
    private JLabel resultadoLabel;
    private JLabel casillaSalidaLabel;
    private JLabel ruletaImageLabel;
    private JLabel mesaImageLabel;

    public RuletaGUI() {
        ruleta = new Ruleta();
        jugador = new Jugador("Jugador de prueba", 20, 1000.0);

        setTitle("Ruleta Casino");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeComponents();
        setupInputValidations();

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
        verEstadisticasButton = new JButton("Ver Estadísticas");


        ruletaImageLabel = new JLabel(new ImageIcon("C:/Users/huasc/OneDrive/Escritorio/Ruleta-Arquisoft/img/fotoRuleta.png"));
        mesaImageLabel = new JLabel(new ImageIcon("C:/Users/huasc/OneDrive/Escritorio/Ruleta-Arquisoft/img/fotoMesa.png"));

        montoApuestaField.setEnabled(false);

        tipoApuestaComboBox = new JComboBox<>();
        tipoApuestaComboBox.addItem("Selecciona tu tipo de apuesta");
        tipoApuestaComboBox.addItem("Apuesta Por Número");
        tipoApuestaComboBox.addItem("Apuesta Por Color");
        tipoApuestaComboBox.addItem("Apuesta Por Docena");
        tipoApuestaComboBox.addItem("Apuesta Falta");
        tipoApuestaComboBox.addItem("Apuesta Pasa");

        verEstadisticasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar estadísticas en una ventana de diálogo
                mostrarVentanaEstadisticas();
            }
        });

        tipoApuestaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) tipoApuestaComboBox.getSelectedItem();
                montoApuestaField.setText("");
                numeroApuestaField.setText("");
                colorApuestaComboBox.setSelectedIndex(0);
                if (seleccion.equals("Apuesta Por Número")) {
                    montoApuestaField.setEnabled(true);
                    numeroApuestaField.setEnabled(true);
                    colorApuestaComboBox.setEnabled(false);
                } else if (seleccion.equals("Apuesta Por Color")) {
                    montoApuestaField.setEnabled(true);
                    numeroApuestaField.setEnabled(false);
                    colorApuestaComboBox.setEnabled(true);
                } else if (seleccion.equals("Apuesta Por Docena")) {
                    montoApuestaField.setEnabled(true);
                    numeroApuestaField.setEnabled(true);
                    colorApuestaComboBox.setEnabled(false);
                } else if (seleccion.equals("Apuesta Falta") || seleccion.equals("Apuesta Pasa")){
                    montoApuestaField.setEnabled(true);
                    numeroApuestaField.setEnabled(false);
                    colorApuestaComboBox.setEnabled(false);
                } else {
                    montoApuestaField.setEnabled(false);
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
                    if (!tipoApuesta.equals("Selecciona tu tipo de apuesta")){
                        JOptionPane.showMessageDialog(null, "Ingrese el monto de la apuesta", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    double montoApuesta = Double.parseDouble(montoApuestaStr);
                    if (montoApuesta > jugador.getSaldo()) {
                        camposValidos = false;
                        JOptionPane.showMessageDialog(null, "El monto de la apuesta no puede ser mayor que el saldo", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                // Verificar campos adicionales según el tipo de apuesta
                if (tipoApuesta.equals("Selecciona tu tipo de apuesta")) {
                    JOptionPane.showMessageDialog(null, "No realizo ninguna apuesta!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (tipoApuesta.equals("Apuesta Por Número")) {
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

        JLabel montoLabel = new JLabel("Monto:");
        JLabel tipoApuestaLabel = new JLabel("Tipo de Apuesta:");
        JLabel numeroApuestaLabel = new JLabel("Número/Docena:");
        JLabel colorApuestaLabel = new JLabel("Color:");

        JPanel panel = new JPanel(new GridLayout(5, 2));
        JPanel imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(1000,500));
        imagePanel.add(ruletaImageLabel);
        imagePanel.add(mesaImageLabel);
        panel.add(tipoApuestaLabel);
        panel.add(tipoApuestaComboBox);
        panel.add(montoLabel);
        panel.add(montoApuestaField);
        panel.add(numeroApuestaLabel);
        panel.add(numeroApuestaField);
        panel.add(colorApuestaLabel);
        panel.add(colorApuestaComboBox);
        panel.add(girarButton);
        panel.add(verEstadisticasButton);

        add(panel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(saldoLabel, BorderLayout.WEST);
        add(resultadoLabel, BorderLayout.EAST);
        add(casillaSalidaLabel, BorderLayout.SOUTH);
    }

    private void mostrarVentanaEstadisticas() {
        // Crear un nuevo JFrame para mostrar las estadísticas
        JFrame estadisticasFrame = new JFrame("Estadísticas del Jugador");
        estadisticasFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crear un JTextArea para mostrar las estadísticas
        JTextArea estadisticasTextArea = new JTextArea();
        estadisticasTextArea.setEditable(false);

        // Obtener las estadísticas del jugador y mostrarlas en el JTextArea
        estadisticasTextArea.append("Estadísticas del jugador " + "\n");
        estadisticasTextArea.append("Ganancias totales: $" + jugador.getGananciasTotales() + "\n");
        estadisticasTextArea.append("Porcentaje de partidas ganadas: " + jugador.getPorcentajePartidasGanadas() + "%\n");
        estadisticasTextArea.append("Porcentaje de partidas perdidas: " + jugador.getPorcentajePartidasPerdidas() + "%\n");

        // Agregar el JTextArea a un JScrollPane para permitir el desplazamiento
        JScrollPane scrollPane = new JScrollPane(estadisticasTextArea);

        // Agregar el JScrollPane al JFrame
        estadisticasFrame.add(scrollPane);

        // Establecer el tamaño y la ubicación de la ventana de estadísticas
        estadisticasFrame.setSize(400, 300);
        estadisticasFrame.setLocationRelativeTo(this);
        estadisticasFrame.setVisible(true);
    }
    private void setupInputValidations() {
        // Validación para el campo de monto
        montoApuestaField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_PERIOD))) {
                    e.consume();
                }
            }
        });

        // Validación para el campo de número
        numeroApuestaField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
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