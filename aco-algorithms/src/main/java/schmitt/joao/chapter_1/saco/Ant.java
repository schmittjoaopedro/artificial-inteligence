package schmitt.joao.chapter_1.saco;

import java.util.ArrayList;
import java.util.List;

public class Ant {

    private int[] path;

    private int currentNode = -1;

    private int targetNode = -1;

    private double distance = 0.0;

    public Ant() {
        clear();
    }

    public void addNode(int i) {
        int[] temp = path;
        path = new int[temp.length + 1];
        for (int j = 0; j < path.length - 1; j++) {
            path[j] = temp[j];
        }
        path[temp.length] = i;
    }

    public int getPredecessor() {
        if (path.length > 0) {
            return path[path.length - 1];
        }
        return -1;
    }

    public void clear() {
        path = new int[0];
    }

    public int getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(int currentNode) {
        this.currentNode = currentNode;
    }

    public int getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(int targetNode) {
        this.targetNode = targetNode;
        this.setDistance(0.0);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int[] getOptimizedPath() {
        List<Integer> finalPath = new ArrayList<Integer>();
        for (int i = 0; i < path.length; i++) {
            for (int j = path.length - 1; j > i; j--) {
                if (path[i] == path[j]) {
                    i = j;
                }
            }
            finalPath.add(path[i]);
        }
        int[] newPath = new int[finalPath.size()];
        for (int n = 0; n < newPath.length; n++) {
            newPath[n] = finalPath.get(n);
        }
        return newPath;
    }

}
