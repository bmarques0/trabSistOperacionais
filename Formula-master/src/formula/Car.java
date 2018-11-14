/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Car extends Thread {

    //Definição de variáveis utilizadas pela classe Car
    private int number;
    private String pilot;
    private Race race;
    private List<Integer> round;

    //Get`s and Set`s
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPilot() {
        return pilot;
    }

    public void setPilot(String pilot) {
        this.pilot = pilot;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public List<Integer> getRound() {
        return round;
    }

    public void setRound(List<Integer> round) {
        this.round = round;
    }

    //Construtor
    public Car(Race race, int number, String pilot) {
        this.race = race;
        this.number = number;
        this.pilot = pilot;
        this.round = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            //Enquanto o carro não tiver finalizado a corrida (não estiver como true no HashMap de controle de carros que já finalizaram a corrida)...
            while (!this.race.getEnd().get(this)) {
                //Verifica se o número de voltas realizados é maior que o número de voltas da corrida
                //Se sim...
                if (this.round.size() >= Race.LAPS) {
                    //"Passa a vez" para o próximo carro incrementando a variável de controle de turno (vez) em 1.
                    Race.TURN++;
                    //Sinaliza que o carro já finalizou a corrida chamando o método que atualiza a refeência no respectivo HashMap. 
                    this.race.endRace(this);
                } else {
                    //Se não...
                    //Caso o carro não tenha terminado a corrida ainda e não tenha completado a volta atual e se for a sua vez:
                    if (!this.race.getLap().get(this) && this.number==Race.TURN) {
                        //Adiciona uma volta na lista de voltas feitas por este carro
                        this.round.add(new Random().nextInt(10) + 40);
                        //Imprime os dados da volta que acabou de completar
                        System.out.println("Car: "+this.getNumber()+" - Time: "+this.round.get(this.round.size() - 1));
                        //Incrementa a variável de turno, passando a vez para o próximo carro
                        Race.TURN++;
                        //Atualiza o HashMap de controle de carros que já concluíram a volta atual
                        this.race.doLap(this);
                        //"Dorme" a quantidade de tempo que foi computado na volta atual
                        sleep(this.round.get(this.round.size() - 1));
                    }
                }
            }
            //Se der m3rd4, imprime a m3rd4
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Método auxiliar para retorno do tempo total gasto na corrida
    public int getTotalTime() {
        int totalTime = 0;
        for (Integer integer : round) {
            totalTime += integer;
        }
        return totalTime;
    }
    
    //Método auxiliar para retorno das parciais de tempo por volta
    public String getPartials(){
        String partials = "";
        for(int i=0; i<=this.round.size()-1;i++){
            partials=partials.concat(" Lap: "+(i+1)+" - "+round.get(i).toString()+" seconds |");
        }
        return partials;
    }
}
