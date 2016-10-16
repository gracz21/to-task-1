package algorithm;

import model.Edge;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Kamil Walkowiak
 */
public class NN extends Algorithm {
    private boolean isDeterministic;
    private LocalSearch localSearch;

    public NN(boolean isDeterministic, Edge incidenceMatrix[][]) {
        super(incidenceMatrix);
        this.isDeterministic = isDeterministic;
        this.localSearch = new LocalSearch(incidenceMatrix);
    }

    public void executeAlgorithm() {
        int currentSolutionValue;
        List<Integer> currentSolution = new ArrayList<>();
        for(int i = 0; i < incidenceMatrix.length; i++) {
            currentSolutionValue = 0;
            currentSolution.clear();
            currentSolution.add(i);

            while(currentSolution.size() < 50) {
                nextIteration(currentSolution);
            }
            currentSolution.add(i);

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
        int selectedVertex;
        int currentVertex =  currentSolution.get(currentSolution.size() - 1);
        Edge topEdges[] = new Edge[3];

        IntStream.range(0, this.incidenceMatrix[currentVertex].length)
                .filter(value -> !currentSolution.contains(value))
                .boxed()
                .filter(vertex -> topEdges[2] == null ||
                        (topEdges[2].getCost() > this.incidenceMatrix[currentVertex][vertex].getCost()))
                .forEach(vertex -> {
                    topEdges[2] = this.incidenceMatrix[currentVertex][vertex];
                    Arrays.sort(topEdges, Comparator.nullsLast((o1, o2) -> Integer.compare(o1.getCost(), o2.getCost())));
                });

        if(isDeterministic) {
            selectedVertex = topEdges[0].getEndVertexNumber();
        } else {
            selectedVertex = topEdges[(new Random()).nextInt(3)].getEndVertexNumber();
        }

        currentSolution.add(selectedVertex);
    }
}
