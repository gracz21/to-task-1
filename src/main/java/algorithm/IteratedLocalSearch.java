package algorithm;

import algorithm.move.Move;
import algorithm.move.SwitchEdges;
import algorithm.move.SwitchVertices;
import model.Edge;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Kamil Walkowiak
 */
public class IteratedLocalSearch {
    private Edge[][] incidenceMatrix;
    private long stopCondition;
    private NN nondeterministicAlgorithm;
    private LocalSearch localSearch;
    private Result result;
    private int numberOfPerturbations;

    public IteratedLocalSearch(Edge[][] incidenceMatrix, long stopCondition, NN nondeterministicAlgorithm, int numberOfPerturbations) {
        this.incidenceMatrix = incidenceMatrix;
        this.stopCondition = stopCondition;
        this.nondeterministicAlgorithm = nondeterministicAlgorithm;
        this.localSearch = new LocalSearch(incidenceMatrix);
        this.result = new Result();
        this.numberOfPerturbations = numberOfPerturbations;
    }

    public void executeAlgorithm() {
        for(int i = 0; i < 10; i++) {
            List<Integer> bestSolution, currentSolution;
            int bestCost, currentCost;

            //Start time measurement
            long startTime = System.nanoTime();

            //Get start solution
            Result startResult = this.nondeterministicAlgorithm.getSingleResult();

            //Initial local search
            this.localSearch.setSolution(startResult.getSolution());
            this.localSearch.setCost(startResult.getMin());
            this.localSearch.executeAlgorithm();

            //Initial solution is currently best
            bestSolution = this.localSearch.getSolution();
            bestCost = this.localSearch.getCost();

            //Main loop
            while(System.nanoTime() - startTime <= this.stopCondition) {
                currentSolution = new ArrayList<>(bestSolution);
                currentCost = bestCost;
                //Add perturbations
                perturbation(currentSolution, currentCost);

                //Run LS for new solution after perturbations
                this.localSearch.setSolution(currentSolution);
                this.localSearch.setCost(currentCost);
                this.localSearch.executeAlgorithm();

                //If new solution is better, then assign it as best solution
                if(this.localSearch.getCost() < bestCost) {
                    bestSolution = this.localSearch.getSolution();
                    bestCost = this.localSearch.getCost();
                }
            }

            result.updateResult(bestSolution, bestCost);
        }
    }

    private void perturbation(List<Integer> solution, int cost) {
        Move move;
        Random random = new Random();

        for(int i = 0; i < numberOfPerturbations; i++) {
            int vertex = solution.get(random.nextInt(solution.size() - 2));
            int prevVertex = solution.get((vertex + solution.size() - 2)%(solution.size() - 1));
            int nextVertex = solution.get((vertex + 1)%(solution.size() - 1));

            if(random.nextBoolean()) {
                //Make vertices switch
                List<Integer> availableVertices = IntStream.range(0, this.incidenceMatrix.length)
                        .filter(o -> !solution.contains(o))
                        .boxed()
                        .collect(Collectors.toList());
                int inVertexIndex = availableVertices.get(random.nextInt(availableVertices.size()));

                move = new SwitchVertices(vertex, prevVertex, nextVertex, inVertexIndex, this.incidenceMatrix, solution);
            } else {
                //Make edges switch
                List<Integer> availableVertices = solution.stream()
                        .filter(o -> o != vertex && o != prevVertex && o != nextVertex)
                        .collect(Collectors.toList());
                int vertex2 = random.nextInt(availableVertices.size());
                int nextVertex2 = solution.get((solution.indexOf(vertex2) + 1)%(solution.size() - 1));

                move = new SwitchEdges(vertex, prevVertex, vertex2, nextVertex2, this.incidenceMatrix, solution);
            }

            move.doMove();
            cost += move.getDelta();
        }
    }

    public void printResults() {
        System.out.println("Min: " + this.result.getMin());
        System.out.println("Avg: " + this.result.getAvg());
        System.out.println("Max: " + this.result.getMax());
        System.out.println(this.result.getSolution());
    }
}
