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
    Result resultAfterLocalSearch;

    Algorithm(List<Vertex> vertices) {
        this.vertices = new LinkedList<>(vertices);
        this.result = new Result();
        this.resultAfterLocalSearch = new Result();
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
        System.out.println("After Local Search:");
        System.out.println("Min: " + this.resultAfterLocalSearch.getMin());
        System.out.println("Avg: " + this.resultAfterLocalSearch.getAvg());
        System.out.println("Max: " + this.resultAfterLocalSearch.getMax());
        System.out.println(this.resultAfterLocalSearch.getSolution());
        System.out.println("Time of Local Search:");
        System.out.println("Min: " + this.resultAfterLocalSearch.getMinTime() + " ms");
        System.out.println("Avg: " + this.resultAfterLocalSearch.getAvgTime() + " ms");
        System.out.println("Max: " + this.resultAfterLocalSearch.getMaxTime() + " ms");
    }
}
