package algorithm.move;

import model.Edge;

import java.util.Collections;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public class SwitchVertices extends Move {
    private int out;
    private int prevToOut;
    private int nextToOut;
    private int in;
    private Edge[][] incidenceMatrix;
    private List<Integer> currentSolution;

    public SwitchVertices(int out, int prevToOut, int nextToOut, int in, Edge[][] incidenceMatrix, List<Integer> currentSolution) {
        this.out = out;
        this.prevToOut = prevToOut;
        this.nextToOut = nextToOut;
        this.in = in;
        this.incidenceMatrix = incidenceMatrix;
        this.currentSolution = currentSolution;
        this.delta = countDelta();
    }

    @Override
    public int countDelta() {
        int currentEdgesCost = this.incidenceMatrix[prevToOut][out].getCost() + this.incidenceMatrix[out][nextToOut].getCost();
        int newEdgesCost = this.incidenceMatrix[prevToOut][in].getCost() + this.incidenceMatrix[in][nextToOut].getCost();

        return newEdgesCost - currentEdgesCost;
    }

    @Override
    public void doMove() {
        Collections.replaceAll(this.currentSolution, this.out, this.in);
    }
}
