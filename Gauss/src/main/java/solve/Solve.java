package solve;

import solve.productions.A;
import solve.productions.B;
import solve.productions.C;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Solve {
    public static void main(String[] args){
        if(args.length<2){
            System.out.println("No args! Should have infile outfile");
            System.exit(1);
        }
        FileIO handler = new FileIO(args[0], args[1]);
        Matrix matrix = new Matrix(handler);
        Scheduler scheduler = new Scheduler(matrix.n*matrix.n); // (n-1)*(n+1)
//        System.out.println(matrix.toString());
        Solve.solve(matrix, scheduler);
//        System.out.println(matrix.toString());
        matrix.saveMatrix();
        scheduler.end();
//        System.exit(0);
    }

    public static void solve(Matrix matrix, Scheduler scheduler){
        for(int i=0; i<matrix.n; i++){
            if(matrix.test(i) && !matrix.repaired(i)){
                System.out.println("Matrix ambiguous!");
                System.exit(6);
            }
            List<Callable<Double>> As = new ArrayList<>();
            for(int k=i+1; k<matrix.n; k++){
                As.add(new A(matrix, i, k));
            }

            List<Double> ratios = scheduler.call(As);
            List<Callable<Double>> Bs = new ArrayList<>();
            for(int k=i+1; k<matrix.n; k++){
                for(int j=i; j<=matrix.n; j++){
                    Bs.add(new B(matrix, ratios.get(k-i-1), i, j, k));
                }
            }

            List<Double> subs = scheduler.call(Bs);
            List<Runnable> Cs = new ArrayList<>();
            int tmp=0;
            for(int k=i+1; k<matrix.n; k++){
                for(int j=i; j<=matrix.n; j++){
                    Cs.add(new C(matrix, subs.get(tmp), i, j, k));
                    tmp++;
                }
            }

            scheduler.run(Cs);
        }
        matrix.cleanup();
    }
}
