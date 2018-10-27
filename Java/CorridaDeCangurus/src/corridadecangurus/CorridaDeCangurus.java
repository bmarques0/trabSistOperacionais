package corridadecangurus;

import java.util.Random;

public class CorridaDeCangurus implements Runnable 
{
    
    public static void main(String[] args) 
    {
        Thread[] cangurus = new Thread [4];
        cangurus[0] = new Thread (new Canguru ("Canguru 1"));
        cangurus[1] = new Thread (new Canguru ("Canguru 2"));
        cangurus[2] = new Thread (new Canguru ("Canguru 3"));
        cangurus[3] = new Thread (new Canguru ("Canguru 4"));
        
    }

        @Override
    public void run() {

    }
    
    
    
    
    
    
    
    public static void NumRandomDistanciaCorrida(){
    
        int numRandom = (int)(100 + Math.random() * (80 - 100 + 1));
        System.out.println(numRandom);
}

    public static void NumRandomPulos(){
    
        int numRandom2 = (int)(10 + Math.random() * (0 - 10 + 1));
        System.out.println(numRandom2);
}

    
    
}