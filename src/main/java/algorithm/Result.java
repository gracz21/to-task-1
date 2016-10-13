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
        min = Integer.MAX_VALUE;
        solution = new LinkedList<>();
    }

    public int getMin() {
        return min;
    }

    public int getAvg() {
        return (int)(avg/100 + 0.5);
    }

    public int getMax() {
        return max;
    }

    public List<Integer> getSolution() {
        return solution;
    }

    void updateResult(List<Integer> solution, int cost) {
        if(this.min > cost) {
            this.min = cost;
            this.solution.clear();
            this.solution.addAll(solution);
        } else if(this.max < cost) {
            this.max = cost;
        }
        avg += cost;
    }
}
