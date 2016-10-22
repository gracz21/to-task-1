package algorithm;

import algorithm.move.Move;
import algorithm.move.SwitchEdges;
import algorithm.move.SwitchVertices;
import model.Edge;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by inf109714 on 11.10.2016.
 */
public class LocalSearch {
    private Edge[][] incidenceMatrix;
    private List<Integer> solution;
    private int cost;
    private long estimatedTime;

    public LocalSearch(Edge[][] incidenceMatrix) {
        this.incidenceMatrix = incidenceMatrix;
    }

    public List<Integer> getSolution() {
        return solution;
    }

    public int getCost() {
        return cost;
    }

    public long getEstimatedTime() {
        return estimatedTime;
    }

    public void setSolution(List<Integer> solution) {
        this.solution = solution;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void executeAlgorithm() {
        long startTime = System.nanoTime();
        List<Move> bestMovesList = new ArrayList<>(50);
        Move bestMove;
        this.solution.stream();
        while(true) {
            bestMovesList.clear();
            bestMovesList.addAll(this.solution.stream().distinct().map(this::findBestMoveForVertex).collect(Collectors.toList()));
            bestMove = bestMovesList.stream().min((o1, o2) -> Integer.compare(o1.getDelta(), o2.getDelta())).get();
            if(bestMove.getDelta() < 0) {
                bestMove.doMove();
                this.cost += bestMove.getDelta();
            } else {
                break;
            }
        }
        this.estimatedTime = System.nanoTime() - startTime;
    }

    private Move findBestMoveForVertex(int currentVertexNumber) {
        Move minimalMove = null, currentMove = null;
        int currentVertexSolutionIndex = this.solution.indexOf(currentVertexNumber);
        int prevVertexNumber = this.solution.get((currentVertexSolutionIndex + this.solution.size() - 2)%(this.solution.size() - 1));
        int nextVertexNumber = this.solution.get((currentVertexSolutionIndex + 1)%(this.solution.size() - 1));

        for(int i = 0; i < this.incidenceMatrix.length; i++) {
            if(!this.solution.contains(i)) {
                currentMove = new SwitchVertices(currentVertexNumber, prevVertexNumber, nextVertexNumber, i, incidenceMatrix, solution);
            } else if(i != currentVertexNumber && i != prevVertexNumber && i != nextVertexNumber) {
                int nextVertexNumber2 = this.solution.get((this.solution.indexOf(i) + 1)%(this.solution.size() - 1));
                currentMove = new SwitchEdges(currentVertexNumber, nextVertexNumber, i, nextVertexNumber2, incidenceMatrix, solution);
            }

            if(minimalMove == null || (currentMove.getDelta() < minimalMove.getDelta())) {
                minimalMove = currentMove;
            }
        }

        return minimalMove;
    }
}
