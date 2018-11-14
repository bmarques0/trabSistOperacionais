/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Race {

    //Definições de variáveis utilizadas na corrida
    //Número de voltas
    public static int LAPS = 10;
    //Número do carro que inicia as voltas
    public static int TURN = 1;
    //HashMap para controle de quais carros já finalizaram a volta atual
    private final HashMap<Car, Boolean> lap = new HashMap<>();
    //HashMap para controle de quais carros já finalizaram a corrida 
    private final HashMap<Car, Boolean> end = new HashMap<>();
    //Lista que conterá os carros participantes da corrida
    private List<Car> racers = new ArrayList<>();
    //Número de carros que participará da corrida
    private int cars;
    //Volta atual - usado apenas para informação no decorrer de término de voltas por todos os carros
    private int currentLap = 1;
    //Variável para controle e confirmação de que todos os carros terminaram a corrida.
    private boolean ended;

    //Hora do Show!
    public void beginRace() {
        System.out.println("########################## BEGIN RACE #########################");
        System.out.println("############################ LAP " + this.currentLap + " ############################");
        //Incrementa em 1 o número da volta atual
        this.currentLap++;
        //Define a quantidade de carros participantes da corrida.
        this.cars = new Random().nextInt(7) + 2;
        //Lista de carros que participantes da corrida (pilotos que sofreram acidentes fatais em corridas)
        this.racers.add(new Car(this, 1, "Ayrton Senna"));
        this.racers.add(new Car(this, 2, "Jules Bianchi"));
        this.racers.add(new Car(this, 3, "Riccardo Paletti"));
        this.racers.add(new Car(this, 4, "Ronnie Peterson"));
        this.racers.add(new Car(this, 5, "Tom Pryce"));
        this.racers.add(new Car(this, 6, "Helmuth Koinigg"));
        this.racers.add(new Car(this, 7, "Roger Williamson"));
        this.racers.add(new Car(this, 8, "Joe Siffert"));
        //Cria a lista de carros participantes da corrida de acordo com a quantidade de carros sorteada.
        this.racers = this.racers.subList(0, this.cars);
        //Define as variáveis de controle da corrida para todos os carros (end => o carro ainda não terminou a corrida), (lap => o carro ainda não terminou a volta)
        for (Car racer : this.racers) {
            this.end.put(racer, false);
            this.lap.put(racer, false);
            racer.start();
        }
        //Enquanto a corrida ainda não tiver terminado...
        while (!this.ended) {
            synchronized (this.end) {
                boolean allEnded = true;
                //Verifica se a flag de término de corrida está "true" para todos os carros.
                for (Car car : this.end.keySet()) {
                    if (!this.end.get(car)) {
                        allEnded = false;
                    }
                }
                //Se estiver a corrida terminou. Então realiza a ordenação da lista de carros de acordo com a soma total do tempo gasto nas voltas, de maneira que
                //o carro que gastou menor tempo para execução da corrida fique em primeiro e assim por diante.
                if (allEnded) {
                    //Ordenação
                    List<Car> classify = this.racers.stream().sorted((r1, r2) -> Integer.compare(r1.getTotalTime(), r2.getTotalTime())).collect(Collectors.toList());
                    //Impressão das informações no SysOut
                    System.out.println("############################ END RACE ############################");
                    for (int i = 0; i <= classify.size() - 1; i++) {
                        System.out.println("Pos. " + (i + 1) + " | Car: " + classify.get(i).getNumber() + " | Pilot: " + classify.get(i).getPilot() + " | Total Time: " + classify.get(i).getTotalTime() + " seconds | Partials: " + classify.get(i).getPartials());
                    }
                    System.out.println("############################ END RACE ############################");
                    //Atualiza a variável de controle (while lá em cima) informando que a corrida acabou.
                    this.ended = true;
                }
            }

            synchronized (this.lap) {
                boolean allEnded = true;
                //Verifica se todos os carros já finalizam a sua volta.
                for (Car car : this.lap.keySet()) {
                    if (!this.lap.get(car)) {
                        allEnded = false;
                    }
                }
                //Se todos os carros já terminaram a volta atual...
                if (allEnded) {
                    //Reinicia o turno para a volta seguinte (o carro de número 1 será o primeiro a executar, o 2 em sequência e assim por diante até todos finalizarem a 
                    //volta
                    this.TURN = 1;
                    //Imprime as informações da volta em questão (parcial)
                    if (this.currentLap <= this.LAPS) {
                        System.out.println("############################ LAP " + this.currentLap + " ############################");
                        //Incrementa em 1 o número da volta atual
                        this.currentLap++;
                    }
                    //Reinicia o HashMap de controle dos carros relacionado ao término da volta, preparando a execução da próxima volta.
                    for (Car car : this.lap.keySet()) {
                        this.lap.put(car, false);
                    }
                }
            }
        }
    }

    //Método que atualiza o HashMap do carro que terminou a volta atual, travando uma nova execução do mesmo na mesma volta.
    public void doLap(Car car) {
        synchronized (this.lap) {
            this.lap.put(car, true);
        }
    }

    //Método que atualiza o HashMap do carro que terminou a corrido, travando uma nova execução do mesmo na corrida. Informa de imediato que o carro terminou a corrida.
    public void endRace(Car car) {
        synchronized (this.end) {
            System.out.println("Car " + car.getNumber() + " ended race!");
            this.end.put(car, true);
        }
    }

    //Get`s utilizados pela classe Car para verificação se o carro pode ou não realizar a volta
    public HashMap<Car, Boolean> getLap() {
        return lap;
    }

    //Get`s utilizados pela classe Car para verificação se o carro pode ou não voltar a correr na corrida
    public HashMap<Car, Boolean> getEnd() {
        return end;
    }

}
