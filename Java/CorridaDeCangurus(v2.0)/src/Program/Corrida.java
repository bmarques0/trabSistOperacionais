package Program;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Corrida {

    private HashMap<Canguru, Boolean> termino = new HashMap<Canguru, Boolean>();
    private HashMap<Canguru, Boolean> passos = new HashMap<Canguru, Boolean>();
    private ArrayList<Canguru> cangurus = new ArrayList<Canguru>();

    public final void IniciarCorrida() {
        Random r = new Random();

        int totalCorrida = (int) (1 + Math.random() * 10);

        // Cria cangurus com nomes estúpidos de cavalo hahaha
        Canguru c1 = new Canguru(this, 1, "Felizberto", totalCorrida);
        Canguru c2 = new Canguru(this, 2, "Mimoso", totalCorrida);
        Canguru c3 = new Canguru(this, 3, "Malandro", totalCorrida);
        Canguru c4 = new Canguru(this, 4, "Vendaval", totalCorrida);
        Canguru c5 = new Canguru(this, 5, "Soberano", totalCorrida);
        cangurus.add(c1);
        cangurus.add(c2);
        cangurus.add(c3);
        cangurus.add(c4);
        cangurus.add(c5);

        // Inicializa threads e roda elas
        for (Canguru c : cangurus) {
            Thread cthread = new Thread(c.Executa);
            cthread.start();
            // Cria flags de controle de fim de corrida e de fim de passo
            termino.put(c, false);
            passos.put(c, false);
        }

        boolean isDone = false;
        // Ficamos no loop até a corrida terminar
        while (!isDone) {
            synchronized (termino) {
                // Todos os cangurus notificaram que acabou a corrida pra eles? Se sim, acabou a corrida.
            for (Canguru canguru : termino.keySet()) {
                if (!termino.get(canguru)) {
                    if (isDone == true) {
                    continue;
                }}}
            }
            // Corrida não acabou, precisamos contar essa rodada
            synchronized (passos) {
                boolean allstepsdone = true;
                // ForEachVerificamos se tem algum canguru que ainda não terminou essa rodada
                for (Canguru canguru : passos.keySet()) {
                    if (!passos.get(canguru)) {
                        allstepsdone = false;
                    }
                }

                // Se todos terminaram a rodada, podemos marcar a flag como falso pra que uma nova rodada se inicie
                if (allstepsdone) {
                    for (int i = 0; i < passos.size(); i++) {
                        for (Canguru canguru : passos.keySet()) {
                            if (!passos.get(canguru)) {
                                passos.put(canguru, false);

                            }
                        }
                    }
                }
            }
        }
        // Ordena cangurus na ordem de quem pulou menos e chegou mais longe.
        List<Canguru> ordenado = this.cangurus.stream().sorted((i1, i2) -> Long.compare(i2.pulos, i1.pulos)).collect(Collectors.toList());

        // Imprime na tela a colocação.
        System.out.println();
        int colocacao = 1;
        for (Canguru c : ordenado) {
            System.out.println("Canguru " + c.Nome + " ficou em " + String.valueOf(colocacao) + "o. lugar. ");
            colocacao++;
        }
    }

    public void NotificaTermino(Canguru canguru) {
        synchronized (termino) {
            if (termino.equals(canguru)) {
                termino.put(canguru, true);
            }
        }
    }

    public void NotificaRodada(Canguru canguru) {
        synchronized (passos) {
            if (passos.equals(canguru)) {
                passos.put(canguru, true);
            }
        }
    }

    public boolean GetStep(Canguru canguru) {
        synchronized (passos) {
            if (passos.equals(canguru)) {
                return passos.get(canguru);
            } else {
                return false;
            }
        }
    }
}
