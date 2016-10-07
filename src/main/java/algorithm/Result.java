package algorithm;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public class Result {
    private int min;
    private int avg;
    private int max;
    private List<Integer> solution;

    Result() {
        solution = new LinkedList<>();
    }

    public int getMin() {
        return min;
    }

    void setMin(int min) {
        this.min = min;
    }

    public int getAvg() {
        return (int)(avg/100 + 0.5);
    }

    void incAvg(int avg) {
        this.avg += avg;
    }

    public int getMax() {
        return max;
    }

    void setMax(int max) {
        this.max = max;
    }

    public List<Integer> getSolution() {
        return solution;
    }

    void setSolution(List<Integer> solution) {
        this.solution.clear();
        this.solution.addAll(solution);
    }
}
