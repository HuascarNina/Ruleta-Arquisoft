public class ApuestaPasa implements Apuesta {
    private double monto;

    public ApuestaPasa(double monto) {
        this.monto = monto;
    }

    @Override
    public double getMonto() {
        return monto;
    }

    @Override
    public boolean esGanadora(Casilla casilla) {
        int numero = casilla.getNumero();
        return numero >= 19 && numero <= 36;
    }
}