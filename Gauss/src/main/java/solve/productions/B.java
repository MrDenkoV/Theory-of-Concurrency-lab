package solve.productions;

import solve.Matrix;

import java.util.concurrent.Callable;

public class B implements Callable<Double> {
    private int i, j, k;
    private Double ratio;
    private Matrix matrix;

    public B(Matrix matrix, Double ratio, int i, int j, int k){
        this.i=i;
        this.j=j;
        this.k=k;
        this.ratio=ratio;
        this.matrix=matrix;
    }

    @Override
    public Double call() throws Exception {
        return matrix.matrix[i][j]*ratio;
    }
}
