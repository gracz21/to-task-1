package algorithm;

/**
 * Created by inf109714 on 18.10.2016.
 */
public class MultipleStartLocalSearch {
    private Algorithm randomAlgorithm;
    private Result result;

    public MultipleStartLocalSearch(Algorithm randomAlgorithm) {
        this.randomAlgorithm = randomAlgorithm;
        result = randomAlgorithm.getResult();
    }

    public void executeAlgoritm() {
        for(int i = 0; i < 10; i++) {
            randomAlgorithm.executeAlgorithm();
        }
    }
}
