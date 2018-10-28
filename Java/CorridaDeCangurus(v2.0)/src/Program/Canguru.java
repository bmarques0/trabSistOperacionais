package Program;

import java.util.Random;

public class Canguru {

    public int Numero;
    public String Nome;
    private int MetrosTotais;

    public int totalPercorrido;
    public int pulos = 0;

    private Random r;
    private Corrida corrida;

    Runnable Executa;

    public Canguru(Corrida c, int numero, String nome, int metrosTotais) {
        this.Numero = numero;
        this.Nome = nome;
        this.MetrosTotais = metrosTotais;
        this.corrida = c;

    }

    public void Executa() throws InterruptedException {
        while (true) {
            // A rodada atual acabou? False se não, true se sim
            if (!corrida.GetStep(this)) {
                // Rodada está pendente, caso canguru não chegou no final ainda, ele dá um pulo
                if (totalPercorrido < MetrosTotais) {
                    r = r = new Random();
                    //int pulo = r.Next(0, 11); // 11 pq é exclusivo, ou seja, 11 não conta, então só vai de 0 a 10.
                    int pulo = (int) (1 + Math.random() * 10);

                    totalPercorrido += pulo;
                    pulos++;

                    // Imprime na tela o pulo do canguru
                    System.out.println("Canguru " + this.Nome + " pulou " + Integer.toString(pulo) + " metros, faltando " + Integer.toString(MetrosTotais - totalPercorrido) + " para o fim da corrida. ");
                    // Notifica que rodada acabou
                    corrida.NotificaRodada(this);
                } else {
                    // Canguru já chegou no final, ele encerra a rodada sem fazer nada
                    corrida.NotificaRodada(this);
                }
            }

            // Caso ele já tenha chego no final, termina a corrida pra ele
            if (totalPercorrido >= MetrosTotais) {
                corrida.NotificaRodada(this);
                corrida.NotificaTermino(this);
            }

            // Espera um pouco pra dar mais emoção hahaha
            Thread.sleep(30);
        }
    }
}
