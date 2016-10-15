package algorithm.move;

import model.Edge;
import model.Vertex;

import java.util.Collections;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public class SwitchVertices extends Move {
    private Vertex out;
    private Vertex in;
    private List<Integer> currentSolution;

    public SwitchVertices(Vertex out, Vertex in, List<Integer> currentSolution) {
        this.out = out;
        this.in = in;
        this.currentSolution = currentSolution;
        this.delta = countDelta();
    }

    @Override
    public int countDelta() {
        int outVertexSolutionIndex = this.currentSolution.indexOf(this.out.getNumber());
        int prevVertexSolutionIndex = outVertexSolutionIndex - 1;
        if(prevVertexSolutionIndex < 0) {
            prevVertexSolutionIndex += currentSolution.size() - 1;
        }
        int nextVertexSolutionIndex = (outVertexSolutionIndex + 1)%(currentSolution.size() - 1);

        int prevVertexNumber = this.currentSolution.get(prevVertexSolutionIndex);
        int nextVertexNumber = this.currentSolution.get(nextVertexSolutionIndex);

        int currentEdgesCost = this.out.getEdges().stream()
                .filter(edge -> edge.getEndVertexNumber() == prevVertexNumber || edge.getEndVertexNumber() == nextVertexNumber)
                .mapToInt(Edge::getCost).sum();
        int newEdgesCost = this.in.getEdges().stream()
                .filter(edge -> edge.getEndVertexNumber() == prevVertexNumber || edge.getEndVertexNumber() == nextVertexNumber)
                .mapToInt(Edge::getCost).sum();

        return newEdgesCost - currentEdgesCost;
    }

    @Override
    public void doMove() {
        this.out.setInSolution(false);
        this.in.setInSolution(true);
        Collections.replaceAll(this.currentSolution, this.out.getNumber(), this.in.getNumber());
    }
}
