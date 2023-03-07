package Filozofowie.arbiter;

import java.util.concurrent.Semaphore;

public class Table {
    final int N;
    final int life;
    private Philosopher[] phils;
    private int seats;
    private Semaphore waiter;
    private Semaphore chops;

    public Table(int n, int life) {
        N = n;
        this.life = life;
        seats=0;
        waiter = new Semaphore(n-1, true);
        chops = new Semaphore(n);
        init();
        start();
        end();
    }

    private void init(){
        phils = new Philosopher[N];
        for(int i=0; i<N; i++){
            phils[i] = new Philosopher(this, i, life);
        }
    }

    private void start(){
        for(int i=0; i<N; i++)
            phils[i].start();
    }

    private void end(){
        try{
            for(int i=0; i<N; i++)
                phils[i].join();
        } catch (InterruptedException e){
            e.printStackTrace();
            System.exit(-1);
        }
        double sum=0;
        for(int i=0; i<N; i++){
            System.out.println(i+" waited for "+phils[i].avgWait());
            sum+=phils[i].avgWait();
        }
        System.out.println("On average they waited for "+sum/N);
    }

    public void takePlace(){
        try {
            waiter.acquire();
        } catch(InterruptedException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void freePlace(){
        waiter.release();
    }

    public void takeChop(){
        try{
            chops.acquire();
        } catch(InterruptedException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void freeChop(){
        chops.release();
    }

    public static void main(String[] arg){
        int n=15;
        int life=100;
        Table table = new Table(n, life);
    }
}
