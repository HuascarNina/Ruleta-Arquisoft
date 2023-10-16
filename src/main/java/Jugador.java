public class Jugador {
   private String nombre;
    private int edad;
    private double saldo;
    private int partidasGanadas;
    private int partidasPerdidas;
    private double gananciasTotales;
    
     public Jugador(String nombre, int edad, double saldoInicial) {
        this.nombre = nombre;
        this.edad = edad;
        this.saldo = saldoInicial;
    }

    public String getNombre() {
        return nombre;
    }

      public int getEdad() {
        return edad;
    }
    
    
    
    public double getSaldo() {
        return saldo;
    }

    public boolean puedeApostar(double monto) {
        return !(saldo <= 0 || monto > saldo);
    }

    public void ganarApuesta(double monto) {
        saldo += monto;
        partidasGanadas++;
        gananciasTotales += monto;
    }

    public void perderApuesta(double monto) {
       saldo -= monto;
        partidasPerdidas++;
        gananciasTotales -= monto;
    }
    
   

    public void mostrarEstadisticas() {
        int totalPartidas = partidasGanadas + partidasPerdidas;
        double porcentajeGanadas = (double) partidasGanadas / totalPartidas * 100;
        double porcentajePerdidas = (double) partidasPerdidas / totalPartidas * 100;
        System.out.println("------------------------------------------------");
        System.out.println("Estadísticas del jugador:" + nombre +"%");
        System.out.println("Ganancias totales: $" + gananciasTotales);
        System.out.println("Porcentaje de partidas ganadas: " + porcentajeGanadas + "%");
        System.out.println("Porcentaje de partidas perdidas: " + porcentajePerdidas + "%");
    }
}

