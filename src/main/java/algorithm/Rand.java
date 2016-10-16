package algorithm;

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
            localSearch.setSolution(currentSolution);
            localSearch.setCost(currentSolutionValue);
            localSearch.executeAlgorithm();
        }

        this.resultAfterLocalSearch = localSearch.getResult();
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
