package solve.productions;

import solve.Matrix;

import java.util.concurrent.Callable;

public class A implements Callable<Double> {
    private int i, k;
    private Matrix matrix;

    public A(Matrix matrix, int i, int k){
        this.i=i;
        this.k=k;
        this.matrix=matrix;
    }

    @Override
    public Double call() throws Exception {
        return matrix.matrix[k][i]/matrix.matrix[i][i];
    }
}
