package algorithm;

import model.Edge;
import model.Graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Kamil Walkowiak
 */
public class GredyCycle {
    private boolean isDeterministic;
    private List<Map<Integer, Integer>> vertices;
    private Result result;

    public GredyCycle(boolean isDeterministic, Graph graph) {
        this.isDeterministic = isDeterministic;
        this.vertices = graph.getVertices().stream().map(
                vertex -> vertex.getEdges().stream().collect(Collectors.toMap(Edge::getNumber, Edge::getCost)))
                .collect(Collectors.toList());
        this.result = new Result();
    }

    public Result getResult() {
        return result;
    }

    public void executeAlgorithm() {
        int currentSolutionValue;
        List<Integer> currentSolution = new LinkedList<>();
        Map.Entry<Integer, Integer> usedEdge;
        for(Map<Integer, Integer> vertex: vertices) {
            currentSolutionValue = 0;
            currentSolution.clear();
            currentSolution.add(vertices.indexOf(vertex));
            usedEdge = vertex.entrySet().stream().sorted((o1, o2) -> Integer.compare(o1.getValue(), o2.getValue()))
                    .findFirst().get();
            currentSolution.add(usedEdge.getKey());
            currentSolution.add(vertices.indexOf(vertex));

            while(currentSolution.size() < 51) {
                selectNextEdge(currentSolution);
            }

            for(int i = 0; i + 1 < currentSolution.size(); i++) {
                currentSolutionValue += vertices.get(currentSolution.get(i)).get(currentSolution.get(i+1));
            }

            if(result.getMin() > currentSolutionValue || result.getMin() == 0) {
                result.setMin(currentSolutionValue);
                result.setSolution(currentSolution);
            } else if(result.getMax() < currentSolutionValue) {
                result.setMax(currentSolutionValue);
            }
            result.incAvg(currentSolutionValue);
        }
    }

    private void selectNextEdge(List<Integer> currentSolution) {
        Map<Integer, Integer> currentCheckedVertex;
        int selectedVertex = -1;
        int currentCost;
        int minCost = Integer.MAX_VALUE;
        int position = 0;
        for(int i = 0; i + 1 < currentSolution.size(); i++) {
            final int index1 = i;
            final int index2 = i+1;
            currentCheckedVertex = vertices.stream().filter(o -> !currentSolution.contains(vertices.indexOf(o)))
                    .sorted((o1, o2) -> Integer.compare(o1.get(currentSolution.get(index1)) + o1.get(currentSolution.get(index2)),
                            o2.get(currentSolution.get(index1)) + o2.get(currentSolution.get(index2)))).findFirst().get();
            currentCost = currentCheckedVertex.get(currentSolution.get(index1)) + currentCheckedVertex.get(currentSolution.get(index2))
                    - vertices.get(currentSolution.get(index1)).get(currentSolution.get(index2));
            if(currentCost < minCost) {
                selectedVertex = vertices.indexOf(currentCheckedVertex);
                minCost = currentCost;
                position = index2;
            }
        }
        currentSolution.add(position, selectedVertex);
    }
}
