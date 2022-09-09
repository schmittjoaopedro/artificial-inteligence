package schmitt.joao.chapter_1.double_bridge;

import java.util.ArrayList;
import java.util.List;

public class Ant {

    private double antSpeed;

    private double antWalked;

    private boolean foundFood = false;

    private List<Vertex> vertices;

    private List<Bridge> bridges;

    private Vertex currentVertex;

    private Bridge currentBridge;

    public Ant(double antSpeed) {
        super();
        this.antSpeed = antSpeed;
    }

    public void clear() {
        this.currentBridge = null;
        this.currentVertex = null;
        this.bridges.clear();
        this.vertices.clear();
        this.antWalked = 0;
    }

    public Vertex getCurrentVertex() {
        return currentVertex;
    }

    public void setCurrentVertex(Vertex currentVertex) {
        this.currentVertex = currentVertex;
    }

    public Bridge getCurrentBridge() {
        return currentBridge;
    }

    public void setCurrentBridge(Bridge currentBridge) {
        this.currentBridge = currentBridge;
    }

    public List<Bridge> getBridges() {
        if(this.bridges == null) this.bridges = new ArrayList<Bridge>();
        return bridges;
    }

    public void setBridges(List<Bridge> bridges) {
        this.bridges = bridges;
    }

    public List<Vertex> getVertices() {
        if(this.vertices == null) this.vertices = new ArrayList<Vertex>();
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public double getAntSpeed() {
        return antSpeed;
    }

    public void setAntSpeed(double antSpeed) {
        this.antSpeed = antSpeed;
    }

    public double getAntWalked() {
        return antWalked;
    }

    public void setAntWalked(double antWalked) {
        this.antWalked = antWalked;
    }

    public boolean isFoundFood() {
        return foundFood;
    }

    public void setFoundFood(boolean foundFood) {
        this.foundFood = foundFood;
    }
}
