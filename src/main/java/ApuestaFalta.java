public class ApuestaFalta implements Apuesta {
    private double monto;

    public ApuestaFalta(double monto) {
        this.monto = monto;
    }

    @Override
    public double getMonto() {
        return monto;
    }

    @Override
    public boolean esGanadora(Casilla casilla) {
        int numero = casilla.getNumero();
        return numero >= 1 && numero <= 18;
    }
}