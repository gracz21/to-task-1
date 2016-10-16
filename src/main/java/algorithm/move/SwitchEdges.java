package algorithm.move;

import model.Edge;

import java.util.Collections;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public class SwitchEdges extends Move {
    private int startEdge1;
    private int endEdge1;
    private int startEdge2;
    private int endEdge2;
    private Edge[][] incidenceMatrix;
    private List<Integer> currentSolution;

    public SwitchEdges(int startEdge1, int endEdge1, int startEdge2, int endEdge2, Edge[][] incidenceMatrix, List<Integer> currentSolution) {
        this.startEdge1 = startEdge1;
        this.endEdge1 = endEdge1;
        this.startEdge2 = startEdge2;
        this.endEdge2 = endEdge2;
        this.incidenceMatrix = incidenceMatrix;
        this.currentSolution = currentSolution;
        this.delta = countDelta();
    }

    @Override
    public int countDelta() {
        int currentCost = incidenceMatrix[startEdge1][endEdge1].getCost() + incidenceMatrix[startEdge2][endEdge2].getCost();
        int newCost = incidenceMatrix[startEdge1][startEdge2].getCost() + incidenceMatrix[endEdge1][endEdge2].getCost();

        return newCost - currentCost;
    }

    @Override
    public void doMove() {
        int reverseStartIndex, reverseEndIndex;
        if(this.currentSolution.indexOf(this.startEdge1) <
                this.currentSolution.indexOf(this.startEdge2)) {
            reverseStartIndex = this.currentSolution.indexOf(this.startEdge1) + 1;
            reverseEndIndex = this.currentSolution.indexOf(this.startEdge2) + 1;
        } else {
            reverseStartIndex = this.currentSolution.indexOf(this.startEdge2) + 1;
            reverseEndIndex = this.currentSolution.indexOf(this.startEdge1) + 1;
        }
        Collections.reverse(this.currentSolution.subList(reverseStartIndex, reverseEndIndex));
    }
}
