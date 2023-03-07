package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public class PC extends AbstractProduction<Vertex> {
    public PC(Vertex _obj, PDrawer<Vertex> _drawer) {
        super(_obj, _drawer);
    }

    @Override
    public Vertex apply(Vertex v) {
        System.out.println("PC");
        Vertex v1 = v.getEast().getSouth().getWest();
        v1.setNorth(v);
        v.setSouth(v1);
        return v;
    }
}
