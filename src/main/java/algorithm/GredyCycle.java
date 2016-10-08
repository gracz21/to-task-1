package algorithm;

import model.Edge;
import model.Graph;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Stream<Map.Entry<Integer, Integer>> sortedVertiesStream;
        Map.Entry<Integer, Integer> currentSelectedVertex;
        int selectedVertex = -1;
        int currentCost;
        int minCost = Integer.MAX_VALUE;
        int position = 0;
        Map<Map.Entry<Integer, Integer>, Integer> currentTopVertices;
        List<Map.Entry<Map.Entry<Integer, Integer>, Integer>> topVertices = null;
        for(int i = 0; i + 1 < currentSolution.size(); i++) {
            final int index1 = i;
            final int index2 = i+1;
            sortedVertiesStream = vertices.stream()
                    .filter(o -> !currentSolution.contains(vertices.indexOf(o)))
                    .collect(Collectors.toMap(o -> vertices.indexOf(o), o -> o.get(currentSolution.get(index1)) + o.get(currentSolution.get(index2))))
                    .entrySet().stream()
                    .sorted((o1, o2) -> Integer.compare(o1.getValue(), o2.getValue()));
            if(isDeterministic) {
                currentSelectedVertex = sortedVertiesStream.findFirst().get();
                currentCost = currentSelectedVertex.getValue() - vertices.get(currentSolution.get(index1)).get(currentSolution.get(index2));
                if(currentCost < minCost) {
                    selectedVertex = currentSelectedVertex.getKey();
                    minCost = currentCost;
                    position = index2;
                }
            } else {
                if(topVertices == null) {
                    topVertices = new ArrayList<>();
                }
                currentTopVertices = sortedVertiesStream.limit(3).collect(Collectors.toMap(Function.identity(), o -> index2));
                currentTopVertices.keySet().forEach(o -> o.setValue(o.getValue() - vertices.get(currentSolution.get(index1)).get(currentSolution.get(index2))));
                topVertices.addAll(currentTopVertices.entrySet());
                topVertices = topVertices.stream()
                        .sorted((o1, o2) -> Integer.compare(o1.getKey().getValue(), o2.getKey().getValue()))
                        .limit(3).collect(Collectors.toList());
            }
        }

        if(!isDeterministic) {
            int rand = (new Random()).nextInt(3);
            selectedVertex = topVertices.get(rand).getKey().getKey();
            position = topVertices.get(rand).getValue();
        }

        currentSolution.add(position, selectedVertex);
    }
}
