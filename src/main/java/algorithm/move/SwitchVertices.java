package algorithm.move;

import model.Edge;
import model.Vertex;

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
        int leftVertexSolutionIndex = outVertexSolutionIndex - 1;
        if(leftVertexSolutionIndex < 0) {
            leftVertexSolutionIndex += currentSolution.size();
        }
        int rightVertexSolutionIndex = (outVertexSolutionIndex + 1)%(currentSolution.size() - 1);

        int leftVertexNumber = this.currentSolution.get(leftVertexSolutionIndex);
        int rightVertexNumber = this.currentSolution.get(rightVertexSolutionIndex);

        int currentEdgesCost = this.out.getEdges().stream()
                .filter(edge -> edge.getEndVertexNumber() == leftVertexNumber || edge.getEndVertexNumber() == rightVertexNumber)
                .mapToInt(Edge::getCost).sum();
        int newEdgesCost = this.in.getEdges().stream()
                .filter(edge -> edge.getEndVertexNumber() == leftVertexNumber || edge.getEndVertexNumber() == rightVertexNumber)
                .mapToInt(Edge::getCost).sum();

        return newEdgesCost - currentEdgesCost;
    }

    @Override
    public void doMove() {
        this.currentSolution.set(this.currentSolution.indexOf(this.out.getNumber()), this.in.getNumber());
    }
}
