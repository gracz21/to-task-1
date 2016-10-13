package algorithm;

import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by inf109714 on 11.10.2016.
 */
public class Rand extends Algorithm {

    public Rand(Graph graph) {
        super(graph);
    }

    public void executeAlgorithm() {
        int currentSolutionValue;
        List<Integer> currentSolution = new LinkedList<>();
        for(Vertex vertex: vertices) {
            currentSolutionValue = 0;
            currentSolution.clear();
            currentSolution.add(vertices.indexOf(vertex));

            while(currentSolution.size() < 50) {
                nextIteration(currentSolution);
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

    @Override
    protected void nextIteration(List<Integer> currentSolution) {
        List<Edge> availableEdges = vertices.get(currentSolution.get(currentSolution.size() - 1))
                .getEdges()
                .stream()
                .filter(e -> !currentSolution.contains(e.getEndVertexNumber()))
                .collect(Collectors.toList());
        int index = new Random().nextInt(availableEdges.size() - 1);
        currentSolution.add(availableEdges.get(index).getEndVertexNumber());
    }
}
