package schmitt.joao.chapter_1.double_bridge;

import java.util.ArrayList;
import java.util.List;

public class Vertex {

    private boolean isSourceFood = false;

    private boolean isNest = false;

    private List<Bridge> bridges;

    public Vertex() {
        super();
    }

    public boolean isSourceFood() {
        return isSourceFood;
    }

    public void setSourceFood(boolean sourceFood) {
        isSourceFood = sourceFood;
    }

    public boolean isNest() {
        return isNest;
    }

    public void setNest(boolean nest) {
        isNest = nest;
    }

    public List<Bridge> getBridges() {
        if(this.bridges == null) this.bridges = new ArrayList<Bridge>();
        return bridges;
    }

    public void setBridges(List<Bridge> bridges) {
        this.bridges = bridges;
    }
}
