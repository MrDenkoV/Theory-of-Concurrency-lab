package Filozofowie.glodni;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
    final int ix;
    Chopstick left, right;
    final int life;
    Random gen = new Random();
    Semaphore sem;
    private long sumWait;
    private long startTime;

    public Philosopher(int i, Chopstick chopl, Chopstick chopr, int life, Semaphore sem) {
        ix=i;
        left=chopl;
        right=chopr;
        this.life=life;
        this.sem=sem;
        sumWait=0;
    }

    private void startEating() throws InterruptedException {
        boolean done=false;
        int time=1;
        while(!done){
            sem.acquire();

            if(!left.used&&!right.used){
                left.used=right.used=true;
                done=true;
            }
            sem.release();
            if(!done)
                Thread.sleep(time);
            time*=2;
        }
        System.out.println(ix+" starts eating");
    }

    private void endEating() throws InterruptedException {
        sem.acquire();
        left.used=right.used=false;
        System.out.println(ix+" ends eating");
        sem.release();
    }

    @Override
    public void run(){
        try {
            for (int i = 0; i < life; i++) {
                startTime = System.currentTimeMillis();
                startEating();
                sumWait += System.currentTimeMillis()-startTime;
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
        return (double) sumWait / life;
    }
}
