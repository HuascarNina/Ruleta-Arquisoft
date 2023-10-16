import java.util.ArrayList;
import java.util.Random;

public class Ruleta {
    private ArrayList<Casilla> casillas;
    private Casilla casillaActual;

    public Ruleta() {
        casillas = new ArrayList<>();
        // Añadimos la casilla verde
        casillas.add(new Casilla(0, "verde"));

        // La ruleta tiene 36 números y 2 colores (rojo y negro)
        for (int i = 1; i <= 36; i++) {
            casillas.add(new Casilla(i, i % 2 == 0 ? "rojo" : "negro"));
        }
    }

    public Casilla girar() {
        Random rand = new Random();
        casillaActual = casillas.get(rand.nextInt(casillas.size()));
        return casillaActual;
    }

    public Casilla getCasillaActual() {
        return casillaActual;
    }
}
