package algorithm;

import model.Graph;
import model.Vertex;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public abstract class Algorithm {
    List<Vertex> vertices;
    Result result;

    Algorithm(Graph graph) {
        vertices = new LinkedList<>(graph.getVertices());
        result = new Result();
    }

    public Result getResult() {
        return result;
    }

    public abstract void executeAlgorithm();

    protected abstract void nextIteration(List<Integer> currentSolution);
}
