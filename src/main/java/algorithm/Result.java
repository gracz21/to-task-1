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
    private long minTime;
    private long avgTime;
    private long maxTime;
    private int solutionsCounter;
    private int timeMeasurementsCounter;

    Result() {
        min = Integer.MAX_VALUE;
        minTime = Long.MAX_VALUE;
        solution = new LinkedList<>();
        solutionsCounter = 0;
        timeMeasurementsCounter = 0;
    }

    public int getMin() {
        return min;
    }

    public int getAvg() {
        return (int)(avg/solutionsCounter + 0.5);
    }

    public int getMax() {
        return max;
    }

    public List<Integer> getSolution() {
        return solution;
    }

    public double getMinTime() {
        return minTime/1000000.0;
    }

    public double getAvgTime() {
        return avgTime/(1000000.0*timeMeasurementsCounter);
    }

    public double getMaxTime() {
        return maxTime/1000000.0;
    }

    void updateResult(List<Integer> solution, int cost) {
        if(this.min > cost) {
            this.min = cost;
            this.solution.clear();
            this.solution.addAll(solution);
        }
        if(this.max < cost) {
            this.max = cost;
        }
        avg += cost;
        solutionsCounter++;
    }

    void updateTimeResult(long time) {
        if(this.minTime > time) {
            this.minTime = time;
        }
        if(this.maxTime < time) {
            this.maxTime = time;
        }
        avgTime += time;
        timeMeasurementsCounter++;
    }
}
