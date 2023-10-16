public class ApuestaPorDocena implements Apuesta {
    private double monto;
    private int docena; // 1 para la primera docena, 2 para la segunda, 3 para la tercera

    public ApuestaPorDocena(double monto, int docena) {
        this.monto = monto;
        this.docena = docena;
    }

    @Override
    public boolean esGanadora(Casilla casilla) {
        int numero = casilla.getNumero();
        return (docena == 1 && numero >= 1 && numero <= 12) ||
               (docena == 2 && numero >= 13 && numero <= 24) ||
               (docena == 3 && numero >= 25 && numero <= 36);
    }

    @Override
    public double getMonto() {
        return monto;
    }
}
