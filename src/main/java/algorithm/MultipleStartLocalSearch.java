package algorithm;

/**
 * Created by inf109714 on 18.10.2016.
 */
public class MultipleStartLocalSearch {
    private Algorithm nondeterministicAlgorithm;
    private Result result;

    public MultipleStartLocalSearch(Algorithm nondeterministicAlgorithm) {
        this.nondeterministicAlgorithm = nondeterministicAlgorithm;
        this.result = new Result();
    }

    public void executeAlgorithm() {
        for(int i = 0; i < 10; i++) {
            long startTime = System.nanoTime();
            for(int j = 0; j < 10; j++) {
                this.nondeterministicAlgorithm.executeAlgorithm();
            }
            long estimatedTime = System.nanoTime() - startTime;

            this.result.updateResult(this.nondeterministicAlgorithm.getResultAfterLocalSearch().getSolution(),
                    this.nondeterministicAlgorithm.getResultAfterLocalSearch().getMin());
            this.result.updateTimeResult(estimatedTime);
        }
    }

    public void printResults() {
        System.out.println("Min: " + this.result.getMin());
        System.out.println("Avg: " + this.result.getAvg());
        System.out.println("Max: " + this.result.getMax());
        System.out.println(this.result.getSolution());
        System.out.println("Times:");
        System.out.println("Min: " + this.result.getMinTime() + " ms");
        System.out.println("Avg: " + this.result.getAvgTime() + " ms");
        System.out.println("Max: " + this.result.getMaxTime() + " ms");
    }
}
