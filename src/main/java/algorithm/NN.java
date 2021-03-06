package algorithm;

import javafx.util.Pair;
import model.Edge;

import java.util.*;
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
            this.localSearch.setSolution(currentSolution);
            this.localSearch.setCost(currentSolutionValue);
            this.localSearch.executeAlgorithm();
            this.resultAfterLocalSearch.updateResult(this.localSearch.getSolution(), this.localSearch.getCost());
            this.resultAfterLocalSearch.updateTimeResult(this.localSearch.getEstimatedTime());
        }
    }

    public Result getSingleResult() {
        int startNode = (new Random()).nextInt(incidenceMatrix.length);
        List<Integer> solution = new ArrayList<>();
        solution.add(startNode);
        int solutionValue = 0;

        while(solution.size() < 50) {
            nextIteration(solution);
        }
        solution.add(startNode);

        for(int i = 0; i + 1 < solution.size(); i++) {
            solutionValue += this.incidenceMatrix[solution.get(i)][solution.get(i+1)].getCost();
        }

        result.updateResult(solution, solutionValue);
        return result;
    }

    Pair<List<Integer>, Integer> getSingleResultAfterLocalSearch() {
        int startNode = (new Random()).nextInt(incidenceMatrix.length);
        List<Integer> solution = new ArrayList<>();
        solution.add(startNode);
        int solutionValue = 0;

        while(solution.size() < 50) {
            nextIteration(solution);
        }
        solution.add(startNode);

        for(int i = 0; i + 1 < solution.size(); i++) {
            solutionValue += this.incidenceMatrix[solution.get(i)][solution.get(i+1)].getCost();
        }

        localSearch.setSolution(solution);
        localSearch.setCost(solutionValue);
        localSearch.executeAlgorithm();

        return new Pair<>(localSearch.getSolution(), localSearch.getCost());
    }

    @Override
    protected void nextIteration(List<Integer> currentSolution) {
        int currentVertex = currentSolution.get(currentSolution.size() - 1);
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
            currentSolution.add(topEdges[0].getEndVertexNumber());
        } else {
            currentSolution.add(topEdges[(new Random()).nextInt(3)].getEndVertexNumber());
        }
    }
}
