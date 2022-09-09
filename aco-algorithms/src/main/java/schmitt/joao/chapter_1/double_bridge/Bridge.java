package schmitt.joao.chapter_1.double_bridge;

public class Bridge {

    private String name;

    private Vertex vertexOne;

    private Vertex vertexTwo;

    private Double size;

    private Double pheromone;

    public Bridge(String name, Vertex vertexOne, Vertex vertexTwo, double size) {
        this.name = name;
        this.vertexOne = vertexOne;
        this.vertexTwo = vertexTwo;
        this.vertexOne.getBridges().add(this);
        this.vertexTwo.getBridges().add(this);
        this.size = size;
        this.pheromone = 1.0;
    }

    public Vertex getVertexOne() {
        return vertexOne;
    }

    public void setVertexOne(Vertex vertexOne) {
        this.vertexOne = vertexOne;
    }

    public Vertex getVertexTwo() {
        return vertexTwo;
    }

    public void setVertexTwo(Vertex vertexTwo) {
        this.vertexTwo = vertexTwo;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Double getPheromone() {
        return pheromone;
    }

    public void setPheromone(Double pheromone) {
        this.pheromone = pheromone;
    }

    public Vertex getWiredVertex(Vertex oneSide) {
        if(vertexOne == oneSide) {
            return vertexTwo;
        } else if (vertexTwo == oneSide) {
            return vertexOne;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
