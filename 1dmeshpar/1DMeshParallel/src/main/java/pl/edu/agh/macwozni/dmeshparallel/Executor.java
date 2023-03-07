package pl.edu.agh.macwozni.dmeshparallel;

import pl.edu.agh.macwozni.dmeshparallel.myProductions.*;
import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.mesh.GraphDrawer;
import pl.edu.agh.macwozni.dmeshparallel.parallelism.BlockRunner;
import pl.edu.agh.macwozni.dmeshparallel.production.IProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

import java.util.LinkedList;
import java.util.List;

public class Executor extends Thread {
    
    private final BlockRunner runner;
    private final int N;
    
    public Executor(BlockRunner _runner, int N){
        this.runner = _runner;
        this.N = N;
    }

    @Override
    public void run() {
//        test();
        algoSQ(N);
    }

    public void algoSQ(int N){
        if (N==0) return;

        PDrawer drawer = new GraphDrawer();
        //axiom
        Vertex s = new Vertex(null, null, null, null, "S");

        //PI
        PI pi = new PI(s, drawer);
        this.runner.addThread(pi);

        //start threads
        this.runner.startAll();

        List<IProduction<Vertex>> prev = new LinkedList<>(), next;
        prev.add(pi);

        for(int i=0; i<2*N-1; i++){
            System.out.println("Step: "+i);
            next = new LinkedList<>();
            if(i<N-1){ //1 PN
                PN pn = new PN(prev.get(0).getObj(), drawer);
                this.runner.addThread(pn);
                next.add(pn);
            }
            for(int j=0; j<prev.size(); j++){
                if(i<N-1){ //PW 1st case
                    PW pw = new PW(prev.get(j).getObj(), drawer);
                    this.runner.addThread(pw);
                    next.add(pw);
                }
                else if(j+1 < prev.size()){ //PW 2nd case
                    PW pw = new PW(prev.get(j).getObj(), drawer);
                    this.runner.addThread(pw);
                    next.add(pw);
                }
                if(i<N && (j==0 || j+1==prev.size())) continue; //skip PC for PN, and for PW in last row
                PC pc = new PC(prev.get(j).getObj(), drawer);
                this.runner.addThread(pc);
            }
            this.runner.startAll();
            prev = next;
        }
    }


    public void test(){
        PDrawer drawer =  new GraphDrawer();
        //axiom
        Vertex s = new Vertex(null, null, null, null, "S");

        //PI
        PI pi = new PI(s, drawer);
        this.runner.addThread(pi);

        //start threads
        this.runner.startAll();

        //PW1, PN1
        PW pw1 = new PW(pi.getObj(), drawer);
        PN pn1 = new PN(pi.getObj(), drawer);
        this.runner.addThread(pw1);
        this.runner.addThread(pn1);

        //start threads
        this.runner.startAll();

        //PW2, PW3, PN2
        PW pw2 = new PW(pw1.getObj(), drawer);
        PW pw3 = new PW(pn1.getObj(), drawer);
        PN pn2 = new PN(pn1.getObj(), drawer);
        this.runner.addThread(pw2);
        this.runner.addThread(pw3);
        this.runner.addThread(pn2);

        //start threads
        this.runner.startAll();

        //pw4, pw5, pc1
        PW pw4 = new PW(pw3.getObj(), drawer);
        PW pw5 = new PW(pn2.getObj(), drawer);
        PC pc1 = new PC(pw3.getObj(), drawer);
        this.runner.addThread(pw4);
        this.runner.addThread(pw5);
        this.runner.addThread(pc1);

        //start threads
        this.runner.startAll();

        //pw6, pc2, pc3
        PW pw6 = new PW(pw5.getObj(), drawer);
        PC pc2 = new PC(pw4.getObj(), drawer);
        PC pc3 = new PC(pw5.getObj(), drawer);
        this.runner.addThread(pw6);
        this.runner.addThread(pc2);
        this.runner.addThread(pc3);

        //start threads
        this.runner.startAll();

        //pc4
        PC pc4 = new PC(pw6.getObj(), drawer);
        this.runner.addThread(pc4);

        //start threads
        this.runner.startAll();
    }
}
