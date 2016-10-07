package algorithm;

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
                currentVertex = vertices.get(usedEdge.getNumber());
                currentSolution.add(usedEdge.getNumber());
                currentSolutionValue += usedEdge.getCost();
            }

            currentSolutionValue += currentVertex.getEdges().stream().
                    filter(e -> e.getNumber() == vertices.indexOf(vertex)).findFirst().get().getCost();
            currentSolution.add(vertices.indexOf(vertex));

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
        Edge selectedEdge;
        if(isDeterministic) {
            selectedEdge =  currentVertex.getEdges().stream().filter(e -> !currentSolution.contains(e.getNumber()))
                    .findFirst().get();
        } else {
            selectedEdge = currentVertex.getEdges().stream().filter(e -> !currentSolution.contains(e.getNumber()))
                    .sorted((o1, o2) -> Integer.compare(o1.getCost(), o2.getCost())).limit(3).collect(Collectors.toList())
                    .get((new Random()).nextInt(3));
        }
        return selectedEdge;
    }
}
