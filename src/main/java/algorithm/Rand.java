package algorithm;

import javafx.util.Pair;
import model.Edge;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by inf109714 on 11.10.2016.
 */
public class Rand extends Algorithm {
    private LocalSearch localSearch;

    public Rand(Edge incidenceMatrix[][]) {
        super(incidenceMatrix);
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

    public Pair<List<Integer>, Integer> getSingleResultAfterLocalSearch() {
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
        int currentVertex =  currentSolution.get(currentSolution.size() - 1);
        List<Integer> availableVertices = IntStream.range(0, this.incidenceMatrix[currentVertex].length)
                .filter(value -> !currentSolution.contains(value))
                .boxed()
                .collect(Collectors.toList());
        currentSolution.add(availableVertices.get(new Random().nextInt(availableVertices.size() - 1)));
    }
}
