package algorithm;

import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by inf109714 on 04.10.2016.
 */
public class NN extends Algorithm {
    private boolean isDeterministic;

    public NN(boolean isDeterministic, Graph graph) {
        super(graph);
        this.isDeterministic = isDeterministic;
        this.vertices.forEach(vertex -> vertex.getEdges().sort((o1, o2) -> Integer.compare(o1.getCost(), o2.getCost())));
    }

    public Result getResult() {
        return result;
    }

    public void executeAlgorithm() {
        int currentSolutionValue;
        List<Integer> currentSolution = new LinkedList<>();
        for(Vertex vertex: this.vertices) {
            currentSolutionValue = 0;
            currentSolution.clear();
            currentSolution.add(this.vertices.indexOf(vertex));

            while(currentSolution.size() < 50) {
                nextIteration(currentSolution);
            }
            currentSolution.add(this.vertices.indexOf(vertex));

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
        Edge selectedEdge;
        Vertex currentVertex = this.vertices.get(currentSolution.get(currentSolution.size() - 1));
        Stream<Edge> availableEdgesStream = currentVertex.getEdges().stream()
                .filter(e -> !currentSolution.contains(e.getEndVertexNumber()));

        if(this.isDeterministic) {
            selectedEdge = availableEdgesStream.findFirst().get();
        } else {
            selectedEdge = availableEdgesStream.limit(3)
                    .collect(Collectors.toList())
                    .get((new Random()).nextInt(3));
        }
        currentSolution.add(selectedEdge.getEndVertexNumber());
    }
}
