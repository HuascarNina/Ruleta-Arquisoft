public class Casilla {
    private int numero;
    private String color;

    public Casilla(int numero, String color) {
        this.numero = numero;
        this.color = color;
    }

    public int getNumero() {
        return numero;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Casilla{" + "numero=" + numero + ", color=" + color + '}';
    }
}
