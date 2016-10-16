package algorithm;

import model.Edge;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Kamil Walkowiak
 */
public class GreedyCycle extends Algorithm {
    private boolean isDeterministic;
    private LocalSearch localSearch;

    public GreedyCycle(boolean isDeterministic, Edge[][] incidenceMatrix) {
        super(incidenceMatrix);
        this.isDeterministic = isDeterministic;
        this.localSearch = new LocalSearch(incidenceMatrix);
    }

    public void executeAlgorithm() {
        int currentSolutionValue;
        List<Integer> currentSolution = new ArrayList<>();
        Edge topEdges[] = new Edge[3];
        for(int i = 0; i < incidenceMatrix.length; i++) {
            int currentStartVertex = i;
            currentSolutionValue = 0;
            currentSolution.clear();
            Arrays.fill(topEdges, null);

            currentSolution.add(currentStartVertex);
            IntStream.range(0, this.incidenceMatrix[currentStartVertex].length)
                    .filter(value -> value != currentStartVertex)
                    .boxed()
                    .filter(vertex -> topEdges[2] == null ||
                            (topEdges[2].getCost() > this.incidenceMatrix[currentStartVertex][vertex].getCost()))
                    .forEach(vertex -> {
                        topEdges[2] = this.incidenceMatrix[currentStartVertex][vertex];
                        Arrays.sort(topEdges, Comparator.nullsLast((o1, o2) -> Integer.compare(o1.getCost(), o2.getCost())));
                    });
            if(this.isDeterministic) {
                currentSolution.add(topEdges[0].getEndVertexNumber());
            } else {
                currentSolution.add(topEdges[(new Random()).nextInt(3)].getEndVertexNumber());
            }
            currentSolution.add(currentStartVertex);

            while(currentSolution.size() <= 50) {
                nextIteration(currentSolution);
            }

            for(int j = 0; j + 1 < currentSolution.size(); j++) {
                currentSolutionValue += this.incidenceMatrix[currentSolution.get(j)][currentSolution.get(j+1)].getCost();
            }

            this.result.updateResult(currentSolution, currentSolutionValue);
            localSearch.setSolution(currentSolution);
            localSearch.setCost(currentSolutionValue);
            localSearch.executeAlgorithm();
        }

        this.resultAfterLocalSearch = localSearch.getResult();
    }

    @Override
    protected void nextIteration(List<Integer> currentSolution) {
        int insertIndex;
        List<Edge> availableEdges;
        Edge selectedEdge;

        availableEdges = IntStream.range(0, this.incidenceMatrix.length)
                .filter(currentSolution::contains)
                .boxed()
                .flatMap(vertex -> Arrays.stream(this.incidenceMatrix[vertex]))
                .filter(edge -> edge != null && !currentSolution.contains(edge.getEndVertexNumber()))
                .sorted((o1, o2) -> Integer.compare(o1.getCost(), o2.getCost()))
                .collect(Collectors.toList());

        if(this.isDeterministic) {
            selectedEdge = availableEdges.stream().findFirst().get();

        } else {
            selectedEdge = availableEdges.stream().limit(3).collect(Collectors.toList()).get((new Random()).nextInt(3));
        }

        insertIndex = currentSolution.indexOf(selectedEdge.getStartVertexNumber()) + 1;
        currentSolution.add(insertIndex, selectedEdge.getEndVertexNumber());
    }
}
