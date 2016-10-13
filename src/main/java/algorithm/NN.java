package algorithm;

import algorithm.Result;
import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by inf109714 on 04.10.2016.
 */
public class NN {
    private boolean isDeterministic;
    private List<Vertex> vertices;
    private Result result;

    public NN(boolean isDeterministic, Graph graph) {
        this.isDeterministic = isDeterministic;
        this.vertices = new LinkedList<>(graph.getVertices());
        this.vertices.forEach(vertex -> vertex.getEdges().sort((o1, o2) -> Integer.compare(o1.getCost(), o2.getCost())));
        result = new Result();
    }

    public Result getResult() {
        return result;
    }

    public void executeAlgorithm() {
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
                currentVertex = vertices.get(usedEdge.getEndVertexNumber());
                currentSolution.add(usedEdge.getEndVertexNumber());
                currentSolutionValue += usedEdge.getCost();
            }

            currentSolutionValue += currentVertex.getEdges().stream().
                    filter(e -> e.getEndVertexNumber() == vertices.indexOf(vertex)).findFirst().get().getCost();
            currentSolution.add(vertices.indexOf(vertex));

            result.updateResult(currentSolution, currentSolutionValue);
        }
    }

    private Edge selectNextEdge(Vertex currentVertex, List<Integer> currentSolution) {
        Edge selectedEdge;
        if(isDeterministic) {
            selectedEdge =  currentVertex.getEdges().stream().filter(e -> !currentSolution.contains(e.getEndVertexNumber()))
                    .findFirst().get();
        } else {
            selectedEdge = currentVertex.getEdges().stream().filter(e -> !currentSolution.contains(e.getEndVertexNumber()))
                    .sorted((o1, o2) -> Integer.compare(o1.getCost(), o2.getCost())).limit(3).collect(Collectors.toList())
                    .get((new Random()).nextInt(3));
        }
        return selectedEdge;
    }
}
