import java.util.*;

public class JuegoRuleta {
    private List<Jugador> jugadores;
    private Ruleta ruleta;

    public JuegoRuleta() {
        this.ruleta = new Ruleta();
        this.jugadores = new ArrayList<>();
    }

    public void iniciarJuego() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¡Bienvenido a la ruleta!");

        System.out.print("Ingresa la cantidad de jugadores (1-8): ");
        int numJugadores = scanner.nextInt();

        if (numJugadores < 1 || numJugadores > 8) {
            System.out.println("Número de jugadores inválido. Debe ser entre 1 y 8.");
            return;
        }

        for (int i = 1; i <= numJugadores; i++) {
            scanner.nextLine(); // Limpiar el buffer del scanner

            System.out.println("Jugador " + i + ":");
            System.out.print("Ingresa tu nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingresa tu edad: ");
            int edad = scanner.nextInt();

            System.out.print("Ingresa tu saldo inicial: $");
            double saldoInicial = scanner.nextDouble();

            Jugador jugador = new Jugador(nombre, edad, saldoInicial);
            jugadores.add(jugador);
        }

        jugar();
    }

    public void jugar() {
    Scanner scanner = new Scanner(System.in);

    while (true) {
        List<Apuesta> apuestas = new ArrayList<>();

        // Fase de apuestas: todos los jugadores hacen sus apuestas
        for (Jugador jugador : jugadores) {
           // Dentro del bucle de jugadores en el método jugar()
            System.out.println("Turno de " + jugador.getNombre() + ". Saldo: $" + jugador.getSaldo());

            double monto;
                do {
                System.out.print("Ingresa el monto de tu apuesta: $");
             monto = scanner.nextDouble();
            if (monto > jugador.getSaldo()) {
              System.out.println("No puedes apostar más dinero del que tienes. Tu saldo es: $" + jugador.getSaldo());
             }
            } while (monto > jugador.getSaldo());

                          System.out.print("¿Qué tipo de apuesta deseas hacer (numero/color/falta/pasa/docena)? ");
        String tipoApuesta = scanner.next();
        Apuesta apuesta = null;

                    
                    
                    
                    
                switch (tipoApuesta.toLowerCase()) {
    case "color":
        System.out.print("¿A qué color deseas apostar (rojo/negro/verde)? ");
        String color = scanner.next();
        apuesta = new ApuestaPorColor(monto, color);
        break;
    case "numero":
        System.out.print("¿A qué número deseas apostar (0-36)? ");
        int numero = scanner.nextInt();
        apuesta = new ApuestaPorNumero(monto, numero);
        break;
    case "docena":
        System.out.print("¿A qué docena deseas apostar (1/2/3)? ");
        int docena = scanner.nextInt();
        apuesta = new ApuestaPorDocena(monto, docena);
        break;
        
          case "falta":
                apuesta = new ApuestaFalta(monto);
                break;

            case "pasa":
                apuesta = new ApuestaPasa(monto);
                break;
}

// Añade la apuesta a la lista de apuestas
                apuestas.add(apuesta);

        }

        // Fase de giro de la ruleta
        Casilla resultado = ruleta.girar();
        System.out.println("La ruleta ha caído en: " + resultado);

        // Fase de resolución de apuestas
for (int i = 0; i < jugadores.size(); i++) {
    Jugador jugador = jugadores.get(i);
    Apuesta apuesta = apuestas.get(i);
    double monto = apuesta.getMonto(); // Obtener el monto de la apuesta

    if (apuesta.esGanadora(resultado)) {
        double ganancia = monto;

        if (apuesta instanceof ApuestaPorNumero) {
            ganancia = monto * 36;
        } else if (apuesta instanceof ApuestaPorColor) {
            ganancia = monto * 2;
        } else if (apuesta instanceof ApuestaPorDocena) {
            ganancia = monto * 3;
        }

        System.out.println(jugador.getNombre() + " ha ganado! Ganaste: $" + ganancia);
        jugador.ganarApuesta(ganancia);
    } else {
        System.out.println(jugador.getNombre() + " ha perdido. Perdiste: $" + monto);
        jugador.perderApuesta(monto);
    }
}


        // Pregunta a los jugadores si quieren jugar otra ronda
        System.out.print("¿Quieres jugar otra vez (si/no)? ");
        String decision = scanner.next();
        if (decision.equalsIgnoreCase("no")) {
            break;
        }
    }

    // Mostrar estadísticas para cada jugador al final del juego
    for (Jugador jugador : jugadores) {
        jugador.mostrarEstadisticas();
    }
}



    public static void main(String[] args) {
        JuegoRuleta juego = new JuegoRuleta();
        juego.iniciarJuego();
    }
}
