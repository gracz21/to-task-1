package algorithm;

import algorithm.move.Move;
import algorithm.move.SwitchEdges;
import algorithm.move.SwitchVertices;
import model.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by inf109714 on 11.10.2016.
 */
public class LocalSearch {
    private List<Vertex> vertices;
    private List<Integer> solution;
    private int cost;
    private Result result;

    public LocalSearch(List<Vertex> vertices) {
        this.vertices = new ArrayList<>(vertices);
        this.result = new Result();
    }

    public Result getResult() {
        return result;
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
        long estimatedTime = System.nanoTime() - startTime;

        this.result.updateResult(solution, cost);
        this.result.updateTimeResult(estimatedTime);
    }

    private Move findBestMoveForVertex(int currentVertexNumber) {
        List<Move> minimalMoves = new ArrayList<>();
        Vertex currentVertex = this.vertices
                .stream()
                .filter(vertex -> vertex.getNumber() == currentVertexNumber)
                .findFirst().get();
        int currentVertexSolutionIndex = this.solution.indexOf(currentVertexNumber);
        int prevVertexSolutionIndex;
        if(currentVertexSolutionIndex - 1 < 0) {
            prevVertexSolutionIndex = this.solution.size() - 2;
        } else {
            prevVertexSolutionIndex = currentVertexSolutionIndex - 1;
        }
        int nextVertexSolutionIndex = (currentVertexSolutionIndex + 1)%(this.solution.size() - 1);

        //Find minimal vertices switch move for current vertex
        minimalMoves.add(this.vertices.stream()
                .filter(vertex -> !vertex.isInSolution())
                .map(vertex -> new SwitchVertices(currentVertex, vertex, this.solution))
                .min((o1, o2) -> Integer.compare(o1.getDelta(), o2.getDelta())).get());

        //Find minimal edges switch move for edge that start in current vertex
        minimalMoves.add(this.vertices.stream()
                .filter(vertex -> vertex.isInSolution() &&
                        vertex.getNumber() != currentVertexNumber &&
                        vertex.getNumber() != this.solution.get(prevVertexSolutionIndex) &&
                        vertex.getNumber() != this.solution.get(nextVertexSolutionIndex)
                )
                .map(vertex -> new SwitchEdges(currentVertex, vertex, vertices.get(solution.get(nextVertexSolutionIndex)), this.solution))
                .min((o1, o2) -> Integer.compare(o1.getDelta(), o2.getDelta())).get());

        return minimalMoves.stream().min((o1, o2) -> Integer.compare(o1.getDelta(), o2.getDelta())).get();
    }
}
