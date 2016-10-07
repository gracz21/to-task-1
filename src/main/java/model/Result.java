package model;

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

    public Result() {
        solution = new LinkedList<>();
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getAvg() {
        return avg/100;
    }

    public void incAvg(int avg) {
        this.avg += avg;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public List<Integer> getSolution() {
        return solution;
    }

    public void setSolution(List<Integer> solution) {
        this.solution.clear();
        this.solution.addAll(solution);
    }
}
