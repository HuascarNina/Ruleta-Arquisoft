import java.util.*;
import java.util.concurrent.*;

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
    ExecutorService executor = Executors.newSingleThreadExecutor();

    while (true) {
        List<Apuesta> apuestas = new ArrayList<>();

        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);
            System.out.println("Turno de " + jugador.getNombre() + ". Saldo: $" + jugador.getSaldo());

            Future<Apuesta> future = executor.submit(new Callable<Apuesta>() {
                @Override
                public Apuesta call() throws Exception {
                        // Obtener la apuesta del jugador
                        System.out.print("Ingresa tu apuesta: $");
                        double monto = scanner.nextDouble();

                        // Asegurarse de que la apuesta no exceda el saldo
                        while (monto > jugador.getSaldo()) {
                            System.out.println("No puedes apostar más dinero del que tienes. Tu saldo es: $" + jugador.getSaldo());
                            System.out.print("Ingresa tu apuesta: $");
                            monto = scanner.nextDouble();
                        }

                        System.out.print("¿Quieres apostar por color, número o docena (color/numero/docena)? ");
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
                        }
                        return apuesta;
                    }
                });

                try {
                Apuesta apuesta = future.get(30, TimeUnit.SECONDS);
                apuestas.add(apuesta);
            } catch (TimeoutException e) {
                System.out.println(jugador.getNombre() + " no apostó a tiempo.");
                if (i == jugadores.size() - 1) {
                    System.out.println("No hay más jugadores. El juego ha terminado.");
                    executor.shutdown();
                    return; // Termina el juego si no hay más jugadores
                } else {
                    System.out.println("Pasando al siguiente jugador.");
                    continue; // Pasa al siguiente jugador si el jugador actual no apostó a tiempo
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

            // Girar la ruleta y verificar apuestas
Casilla resultado = ruleta.girar();
System.out.println("La ruleta ha caído en: " + resultado);

for (int i = 0; i < jugadores.size(); i++) {
    Jugador jugador = jugadores.get(i);
    Apuesta apuesta = apuestas.get(i);

    if (apuesta.esGanadora(resultado)) {
        double montoGanado = apuesta.getMonto() * 2; // Ajusta la ganancia según las reglas de tu juego
        System.out.println(jugador.getNombre() + " ha ganado! Ganaste: $" + montoGanado);
        jugador.ganarApuesta(montoGanado);
    } else {
        System.out.println(jugador.getNombre() + " ha perdido. Perdiste: $" + apuesta.getMonto());
        jugador.perderApuesta(apuesta.getMonto());
    }
}


      System.out.print("¿Quieres jugar otra vez (si/no)? ");
        String decision = scanner.next();
        if (decision.equalsIgnoreCase("no")) {
            break;
        }
    }

    executor.shutdown();
    mostrarEstadisticas(); // Mostrar estadísticas al final del juego
}

private void mostrarEstadisticas() {
    // Mostrar estadísticas para cada jugador
    for (Jugador jugador : jugadores) {
        jugador.mostrarEstadisticas();
    }
}
    
    public static void main(String[] args) {
        JuegoRuleta juego = new JuegoRuleta();
        juego.iniciarJuego();
    }
}
