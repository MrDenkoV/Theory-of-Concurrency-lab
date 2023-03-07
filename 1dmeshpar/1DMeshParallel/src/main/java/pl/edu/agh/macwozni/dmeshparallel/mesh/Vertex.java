package pl.edu.agh.macwozni.dmeshparallel.mesh;

public class Vertex {

    //label
    String mLabel;
    //links to adjacent elements
//    Vertex mLeft;
//    Vertex mRight;
    Vertex mN;
    Vertex mE;
    Vertex mS;
    Vertex mW;

    //methods for adding links
    public Vertex(Vertex _north, Vertex _east, Vertex _south, Vertex _west, String _lab) {
        this.mN = _north;
        this.mE = _east;
        this.mS = _south;
        this.mW = _west;
//        this.mLeft = _left;
//        this.mRight = _right;
        this.mLabel = _lab;
    }
    //empty constructor

    public Vertex() {
    }

//    public void setLeft(Vertex _left) {
//        this.mLeft = _left;
//    }
//
//    public void setRight(Vertex _right) {
//        this.mRight = _right;
//    }

    public void setNorth(Vertex _north){
        this.mN = _north;
    }

    public void setEast(Vertex _east){
        this.mE = _east;
    }

    public void setSouth(Vertex _south){
        this.mS = _south;
    }

    public void setWest(Vertex _west){
        this.mW = _west;
    }

    public void setLabel(String _lab) {
        this.mLabel = _lab;
    }

//    public Vertex getLeft() {
//        return this.mLeft;
//    }
//
//    public Vertex getRight() {
//        return this.mRight;
//    }

    public Vertex getNorth(){
        return this.mN;
    }

    public Vertex getEast(){
        return this.mE;
    }

    public Vertex getSouth(){
        return this.mS;
    }

    public Vertex getWest(){
        return this.mW;
    }

    public String getLabel() {
        return this.mLabel;
    }
}
