public class ApuestaPorNumero implements Apuesta {
    private double monto;
    private int numero;

    public ApuestaPorNumero(double monto, int numero) {
        this.monto = monto;
        this.numero = numero;
    }

    @Override
    public boolean esGanadora(Casilla casilla) {
        return casilla.getNumero() == numero;
    }

    @Override
    public double getMonto() {
        return monto;
    }
}
