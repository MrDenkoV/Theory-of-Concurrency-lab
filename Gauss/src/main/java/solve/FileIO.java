package solve;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class FileIO {
    private final String nameOut;
    private List<String> lines;

    public FileIO(String nameIn, String nameOut){
        this.nameOut=nameOut;
        lines = Collections.emptyList();
        try{
            lines = Files.readAllLines(Paths.get(nameIn));
//            System.out.println(lines);
        } catch(Exception e){
            System.out.println("Error reading " + nameIn + " file!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public int getN(){
        return Integer.parseInt(lines.get(0));
    }

    public void getMatrix(Matrix matrix){
        for(int i=0; i<matrix.n; i++){
            String[] tmp = lines.get(i+1).split(" ");
            for(int j=0; j<matrix.n; j++){
                matrix.matrix[i][j] = Double.parseDouble(tmp[j]);
            }
        }
        String[] tmp = lines.get(matrix.n+1).split(" ");
        for(int i=0; i<matrix.n; i++){
            matrix.matrix[i][matrix.n] = Double.parseDouble(tmp[i]);
        }
    }

    public void saveFile(String matrix){
        try {
            Files.writeString(Paths.get(nameOut), matrix);
        } catch (Exception e) {
            System.out.println("Error creating and saving to " + nameOut + " file!");
            e.printStackTrace();
            System.exit(1);
        }
    }

}
