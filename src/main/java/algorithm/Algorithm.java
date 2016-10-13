package algorithm;

import model.Graph;
import model.Vertex;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public abstract class Algorithm {
    protected List<Vertex> vertices;
    protected Result result;

    public Algorithm(Graph graph) {
        vertices = new LinkedList<>(graph.getVertices());
        result = new Result();
    }

    public Result getResult() {
        return result;
    }

    public abstract void executeAlgorithm();

    protected abstract void nextIteration();
}
