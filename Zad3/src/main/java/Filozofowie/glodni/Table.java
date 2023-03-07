package Filozofowie.glodni;

import java.util.concurrent.Semaphore;

public class Table {
    final int N;
    final int life;
    private Chopstick[] chops;
    private Philosopher[] phils;
    private Semaphore sem = new Semaphore(1);

    public Table(int n, int life) {
        N = n;
        this.life = life;
        init();
        start();
        end();
    }

    private void init(){
        chops = new Chopstick[N];
        phils = new Philosopher[N];
        for(int i=0; i<N; i++)
            chops[i] = new Chopstick();
        for (int i=0; i<N; i++)
            phils[i] = new Philosopher(i, chops[i], chops[(i+1)%N], life, sem);
    }

    private void start(){
        for(int i=0; i<N; i++){
            phils[i].start();
        }
    }

    private void end(){
        try {
            for (int i = 0; i < N; i++) {
                phils[i].join();
            }
        } catch(InterruptedException e){
            e.printStackTrace();
            System.exit(-1);
        }
        double sum=0;
        for(int i=0; i<N; i++){
            System.out.println(i+" waited on avg for " + phils[i].avgWait());
            sum+=phils[i].avgWait();
        }
        System.out.println("\nOn average they waited for " + sum/N);
    }

    public static void main(String[] args){
        int n=15;
        int life=100;
        Table table = new Table(n, life);
    }
}
