package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public class PN extends AbstractProduction<Vertex> {
    public PN(Vertex _obj, PDrawer<Vertex> _drawer) {
        super(_obj, _drawer);
    }

    @Override
    public Vertex apply(Vertex v) {
        System.out.println("PN");
        Vertex res = new Vertex(null, null, v, null, "M");
        v.setNorth(res);
        return res;
    }
}
