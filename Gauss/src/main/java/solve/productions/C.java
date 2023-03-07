package solve.productions;

import solve.Matrix;

public class C implements Runnable{
    private int i, j, k;
    private Double sub;
    private Matrix matrix;

    public C(Matrix matrix, Double sub, int i, int j, int k){
        this.i=i;
        this.j=j;
        this.k=k;
        this.sub=sub;
        this.matrix=matrix;
    }

    @Override
    public void run() {
        matrix.matrix[k][j]-=sub;
    }
}
