package algorithm.move;

import model.Vertex;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Kamil Walkowiak
 */
public class SwitchEdges extends Move {
    private Vertex startVertexEdge1;
    private Vertex startVertexEdge2;
    private Vertex endVertexEdge1;
    private int endVertexNumberEdge2;
    private List<Integer> currentSolution;

    public SwitchEdges(Vertex startVertex1, Vertex startVertex2, Vertex endVertexEdge1, List<Integer> currentSolution) {
        this.startVertexEdge1 = startVertex1;
        this.startVertexEdge2 = startVertex2;
        this.currentSolution = currentSolution;
        this.endVertexEdge1 = endVertexEdge1;
        this.endVertexNumberEdge2 = this.currentSolution.get(
                (this.currentSolution.indexOf(startVertex2.getNumber()) + 1)%(this.currentSolution.size() - 1));
        this.delta = countDelta();
    }

    @Override
    public int countDelta() {
        int currentCost = startVertexEdge1.getEdges().stream()
                .filter(edge -> edge.getEndVertexNumber() == endVertexEdge1.getNumber())
                .findFirst().get().getCost() +
                startVertexEdge2.getEdges().stream()
                        .filter(edge -> edge.getEndVertexNumber() == endVertexNumberEdge2)
                        .findFirst().get().getCost();
        int newCost = startVertexEdge1.getEdges().stream()
                .filter(edge -> edge.getEndVertexNumber() == startVertexEdge2.getNumber())
                .findFirst().get().getCost() +
                endVertexEdge1.getEdges().stream()
                        .filter(edge -> edge.getEndVertexNumber() == endVertexNumberEdge2)
                        .findFirst().get().getCost();
        return newCost - currentCost;
    }

    @Override
    public void doMove() {
        int reverseStartIndex, reverseEndIndex;
        if(this.currentSolution.indexOf(this.startVertexEdge1.getNumber()) <
                this.currentSolution.indexOf(this.startVertexEdge2.getNumber())) {
            reverseStartIndex = this.currentSolution.indexOf(this.startVertexEdge1.getNumber()) + 1;
            reverseEndIndex = this.currentSolution.indexOf(this.startVertexEdge2.getNumber()) + 1;
        } else {
            reverseStartIndex = this.currentSolution.indexOf(this.startVertexEdge2.getNumber()) + 1;
            reverseEndIndex = this.currentSolution.indexOf(this.startVertexEdge1.getNumber()) + 1;
        }
        Collections.reverse(this.currentSolution.subList(reverseStartIndex, reverseEndIndex));
    }
}
