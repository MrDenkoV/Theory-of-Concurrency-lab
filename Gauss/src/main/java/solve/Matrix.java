package solve;

import java.util.ArrayList;

public class Matrix {
    private FileIO handler;
    public int n;
    public Double[][] matrix;

    public Matrix(FileIO handler){
        this.handler = handler;
        readMatrix();
    }

    private void readMatrix(){
        this.n = handler.getN();
        matrix = new Double[n][n+1];
        handler.getMatrix(this);
    }

    public void saveMatrix(){
        handler.saveFile(this.toString());
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(int i=0; i<n; i++){
            for(int j=0; j<n-1; j++){
                res.append(matrix[i][j].toString()).append(' ');
            }
            res.append(matrix[i][n - 1].toString()).append('\n');
        }
        for(int i=0; i<n-1; i++){
            res.append(matrix[i][n]).append(' ');
        }
        res.append(matrix[n-1][n]).append('\n');
        return Integer.toString(n)+'\n'+res.toString();
    }

    public boolean test(int i){
        return Math.abs(this.matrix[i][i])<0.00001;
    }

    public void swapRows(int i, int j){
        Double tmp;
        for(int x=i; x<=this.n; x++){
            tmp=this.matrix[i][x];
            this.matrix[i][x]=this.matrix[j][x];
            this.matrix[j][x]=tmp;
        }
    }

    public boolean repaired(int i){
        for(int j=i+1; j<this.n; j++){
            if(Math.abs(this.matrix[j][i])>0.00001){
                swapRows(i, j);
                return true;
            }
        }
        return false;
    }

    public void cleanup(){
        for(int i=n-1; i>=0; i--){
            matrix[i][n]=matrix[i][n]/matrix[i][i];
            matrix[i][i]=matrix[i][i]/matrix[i][i];
            for(int k=0; k<i; k++){
                Double ratio=matrix[k][i]/matrix[i][i];
                matrix[k][n]=matrix[k][n]-matrix[i][n]*ratio;
                matrix[k][i]=matrix[k][i]-matrix[i][i]*ratio;
            }
        }
    }
}
