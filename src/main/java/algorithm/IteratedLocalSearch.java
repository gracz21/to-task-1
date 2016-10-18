package algorithm;

import model.Edge;

/**
 * Created by inf109714 on 18.10.2016.
 */
public class IteratedLocalSearch {
    private Edge[][] incidenceMatrix;
    private long stopCondition;
    private Result currentResult;
    private LocalSearch localSearch;
    private Result result;

    public IteratedLocalSearch(Edge[][] incidenceMatrix, long stopCondition, Result currentResult) {
        this.incidenceMatrix = incidenceMatrix;
        this.stopCondition = stopCondition;
        this.currentResult = currentResult;
        this.localSearch = new LocalSearch(incidenceMatrix);
        this.result = new Result();
    }

    public void executeAlgoritm() {
        long startTime = System.nanoTime();
        localSearch.setSolution(currentResult.getSolution());
        localSearch.setCost(currentResult.getMin());
        localSearch.executeAlgorithm();

        while(System.nanoTime() - startTime <= stopCondition) {
            //perturbation

        }
    }
}
