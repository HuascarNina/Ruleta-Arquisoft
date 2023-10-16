
import java.util.Arrays;

public class Tablero {
    private Casilla[][] matriz;

    public Tablero() {
        matriz = new Casilla[3][12];
        int numero = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 12; j++) {
                String color;
                if (numero == 0) {
                    color = "verde";
                } else if (Arrays.asList(2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35).contains(numero)) {
                    color = "negro";
                } else {
                    color = "rojo";
                }
                matriz[i][j] = new Casilla(numero++, color);
            }
        }
    }

    public Casilla getCasilla(int fila, int columna) {
        return matriz[fila][columna];
    }
}
