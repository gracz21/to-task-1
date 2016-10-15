package algorithm;

import model.Vertex;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public abstract class Algorithm {
    List<Vertex> vertices;
    Result result;

    Algorithm(List<Vertex> vertices) {
        this.vertices = new LinkedList<>(vertices);
        this.result = new Result();
    }

    public Result getResult() {
        return result;
    }

    public abstract void executeAlgorithm();

    protected abstract void nextIteration(List<Integer> currentSolution);

    public void printResults() {
        System.out.println("Min: " + this.result.getMin());
        System.out.println("Avg: " + this.result.getAvg());
        System.out.println("Max: " + this.result.getMax());
        System.out.println(this.result.getSolution());
    }
}
