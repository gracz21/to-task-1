package algorithm;

import model.Edge;
import model.Vertex;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kamil Walkowiak
 */
public class GreedyCycle extends Algorithm {
    private boolean isDeterministic;

    public GreedyCycle(boolean isDeterministic, List<Vertex> vertices) {
        super(vertices);
        this.isDeterministic = isDeterministic;
    }

    public void executeAlgorithm() {
        int currentSolutionValue;
        List<Integer> currentSolution = new LinkedList<>();
        Stream<Edge> sortedEdges;
        Edge usedEdge;
        for(Vertex vertex: this.vertices) {
            this.vertices.forEach(vertex1 -> vertex1.setInSolution(false));
            currentSolutionValue = 0;
            currentSolution.clear();

            currentSolution.add(this.vertices.indexOf(vertex));
            vertex.setInSolution(true);
            sortedEdges = vertex.getEdges().stream().sorted((o1, o2) -> Integer.compare(o1.getCost(), o2.getCost()));
            if(this.isDeterministic) {
                usedEdge = sortedEdges.findFirst().get();
            } else {
                usedEdge = sortedEdges.limit(3).collect(Collectors.toList()).get((new Random()).nextInt(3));
            }
            currentSolution.add(usedEdge.getEndVertexNumber());
            this.vertices.get(usedEdge.getEndVertexNumber()).setInSolution(true);
            currentSolution.add(this.vertices.indexOf(vertex));

            while(currentSolution.size() < 51) {
                nextIteration(currentSolution);
            }

            for(int i = 0; i + 1 < currentSolution.size(); i++) {
                int index = i;
                currentSolutionValue += this.vertices.get(currentSolution.get(i)).getEdges().stream()
                        .filter(edge -> edge.getEndVertexNumber() == currentSolution.get(index+1))
                        .findFirst().get().getCost();
            }

            this.result.updateResult(currentSolution, currentSolutionValue);
        }
    }

    @Override
    protected void nextIteration(List<Integer> currentSolution) {
        int insertIndex;
        Edge selectedEdge;

        List<Edge> availableEdges = this.vertices.stream()
                .filter(Vertex::isInSolution)
                .flatMap(vertex -> vertex.getEdges().stream())
                .filter(edge -> !(currentSolution.contains(edge.getEndVertexNumber())))
                .sorted((o1, o2) -> Integer.compare(o1.getCost(), o2.getCost()))
                .collect(Collectors.toList());

        if(this.isDeterministic) {
            selectedEdge = availableEdges.stream().findFirst().get();

        } else {
            selectedEdge = availableEdges.stream().limit(3).collect(Collectors.toList()).get((new Random()).nextInt(3));
        }

        insertIndex = currentSolution.indexOf(selectedEdge.getStartVertexNumber()) + 1;
        currentSolution.add(insertIndex, selectedEdge.getEndVertexNumber());
        this.vertices.get(selectedEdge.getEndVertexNumber()).setInSolution(true);
    }
}
