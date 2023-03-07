package Filozofowie.arbiter;

import java.util.Random;

public class Philosopher extends Thread{
    private final Table tab;
    private final int ix;
    private final int life;
    private long sumWait;
    private long startWait;
    Random gen = new Random();


    public Philosopher(Table tab, int ix, int life) {
        this.tab = tab;
        this.ix = ix;
        this.life = life;
        sumWait = 0;
    }

    public void startEating(){
        tab.takePlace();
        tab.takeChop();
        tab.takeChop();
        System.out.println(ix+" start eating");
    }

    public void endEating(){
        tab.freeChop();
        tab.freeChop();
        tab.freePlace();
        System.out.println(ix+" ends eating");
    }

    @Override
    public void run(){
        try{
            for(int i=0; i<life; i++){
                startWait = System.currentTimeMillis();
                startEating();
                sumWait += System.currentTimeMillis()-startWait;
                Thread.sleep(1);
                endEating();
                Thread.sleep(gen.nextInt(1000));
            }
            System.out.println(ix+" ends");
        } catch(InterruptedException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public double avgWait(){
        return (double) sumWait/life;
    }
}
