package algorithm;

import algorithm.Result;
import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kamil Walkowiak
 */
public class GreedyCycle {
    private boolean isDeterministic;
    private List<Vertex> vertices;
    private Result result;

    public GreedyCycle(boolean isDeterministic, Graph graph) {
        this.isDeterministic = isDeterministic;
        this.vertices = graph.getVertices();
        this.result = new Result();
    }

    public Result getResult() {
        return result;
    }

    public void executeAlgorithm() {
        int currentSolutionValue;
        List<Integer> currentSolution = new LinkedList<>();
        Stream<Edge> sortedEdges;
        Edge usedEdge;
        for(Vertex vertex: vertices) {
            vertices.forEach(vertex1 -> vertex1.setInSolution(false));
            currentSolutionValue = 0;
            currentSolution.clear();
            currentSolution.add(vertices.indexOf(vertex));
            vertex.setInSolution(true);
            sortedEdges = vertex.getEdges().stream().sorted((o1, o2) -> Integer.compare(o1.getCost(), o2.getCost()));
            if(isDeterministic) {
                usedEdge = sortedEdges.findFirst().get();
            } else {
                usedEdge = sortedEdges.limit(3).collect(Collectors.toList()).get((new Random()).nextInt(3));
            }
            currentSolution.add(usedEdge.getEndVertexNumber());
            vertices.get(usedEdge.getEndVertexNumber()).setInSolution(true);
            currentSolution.add(vertices.indexOf(vertex));

            while(currentSolution.size() < 51) {
                selectNextEdge(currentSolution);
            }

            for(int i = 0; i + 1 < currentSolution.size(); i++) {
                int index = i;
                currentSolutionValue += vertices.get(currentSolution.get(i)).getEdges().stream()
                        .filter(edge -> edge.getEndVertexNumber() == currentSolution.get(index+1))
                        .findFirst().get().getCost();
            }

            result.updateResult(currentSolution, currentSolutionValue);
        }
    }

    private void selectNextEdge(List<Integer> currentSolution) {
        int insertIndex;
        Edge selectedEdge;

        List<Edge> availableEdges = vertices.stream()
                .filter(Vertex::isInSolution)
                .flatMap(vertex -> vertex.getEdges().stream())
                .filter(edge -> !(currentSolution.contains(edge.getEndVertexNumber())))
                .sorted((o1, o2) -> Integer.compare(o1.getCost(), o2.getCost()))
                .collect(Collectors.toList());

        if(isDeterministic) {
            selectedEdge = availableEdges.stream().findFirst().get();

        } else {
            selectedEdge = availableEdges.stream().limit(3).collect(Collectors.toList()).get((new Random()).nextInt(3));
        }

        insertIndex = currentSolution.indexOf(selectedEdge.getStartVertexNumber()) + 1;
        currentSolution.add(insertIndex, selectedEdge.getEndVertexNumber());
        vertices.get(selectedEdge.getEndVertexNumber()).setInSolution(true);
    }
}
