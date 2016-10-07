package algorithm;

import model.Edge;
import model.Graph;
import model.Result;
import model.Vertex;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by inf109714 on 04.10.2016.
 */
public class NN {
    private Graph graph;
    private Result result;

    public NN(Graph graph) {
        this.graph = graph;
        result = new Result();
    }

    public Result getResult() {
        return result;
    }

    public void executeAlgorithm() {
        List<Vertex> vertices = graph.getVertices();
        int currentSolutionValue;
        List<Integer> currentSolution = new LinkedList<>();
        Vertex currentVertex;
        Edge usedEdge;
        for(Vertex vertex: vertices) {
            currentSolutionValue = 0;
            currentVertex = vertex;
            currentSolution.clear();
            currentSolution.add(vertices.indexOf(vertex));

            while(currentSolution.size() < 50) {
                usedEdge = selectNextEdge(currentVertex, currentSolution);
                currentVertex = vertices.get(usedEdge.getNumber());
                currentSolution.add(usedEdge.getNumber());
                currentSolutionValue += usedEdge.getCost();
            }

            currentSolutionValue += currentVertex.getEdges().stream().
                    filter(e -> e.getNumber() == vertices.indexOf(vertex)).findFirst().get().getCost();

            if(result.getMin() > currentSolutionValue || result.getMin() == 0) {
                result.setMin(currentSolutionValue);
                result.setSolution(currentSolution);
            } else if(result.getMax() < currentSolutionValue) {
                result.setMax(currentSolutionValue);
            }
            result.incAvg(currentSolutionValue);
        }
    }

    private Edge selectNextEdge(Vertex currentVertex, List<Integer> currentSolution) {
        return currentVertex.getEdges().stream().filter(e -> !currentSolution.contains(e.getNumber())).findFirst().get();
    }
}
