public class ApuestaPorColor implements Apuesta {
    private double monto;
    private String color;

    public ApuestaPorColor(double monto, String color) {
        this.monto = monto;
        this.color = color;
    }

    @Override
    public boolean esGanadora(Casilla casilla) {
        return casilla.getColor().equalsIgnoreCase(color);
    }

    @Override
    public double getMonto() {
        return monto;
    }
}
