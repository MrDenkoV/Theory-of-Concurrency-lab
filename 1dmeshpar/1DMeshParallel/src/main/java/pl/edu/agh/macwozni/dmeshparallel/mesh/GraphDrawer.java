package pl.edu.agh.macwozni.dmeshparallel.mesh;

import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public class GraphDrawer implements PDrawer<Vertex> {
    @Override
    public void draw(Vertex v) {
        //go east
        while(v.mE != null){
            v = v.mE;
        }
        //go south
        while(v.mS != null){
            v = v.mS;
        }
        //go east
        while(v.mE != null){
            v = v.mE;
        }
        Vertex SE = v;
        int N=0, M=0;
        //go west
        while(v.mW != null){
            v = v.mW;
            N++;
        }
        v = SE;
        //go north
        while(v.mN != null){
            v = v.mN;
            M++;
        }
        StringBuilder res = new StringBuilder();
        while(v != null){
            M--;
//            System.out.print(rowsToString(v, N));
//            System.out.println("?");
            res.append(rowsToString(v, N));
            v = v.mS;
        }
        System.out.println(res.toString());
    }

    String rowsToString(Vertex v, int N){
        StringBuilder line1 = new StringBuilder("\n");
        StringBuilder line2 = new StringBuilder("\n");
        for(int i=0; i<=N; i++){
            if(v!=null) {
                line1.append("M");
                if (v.mW != null)
                    line1.append("-");
                else
                    line1.append(" ");
                if(v.mS != null)
                    line2.append("| ");
                else
                    line2.append("  ");
                v = v.mW;
            }
            else {
                line1.append("  ");
                line2.append("  ");
            }
        }
        return line1.reverse().append(line2.reverse()).toString();
    }

}
