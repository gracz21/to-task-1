package algorithm;

import algorithm.move.Move;
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

    public LocalSearch(List<Vertex> vertices, List<Integer> solution, int cost) {
        this.vertices = new ArrayList<>(vertices);
        this.solution = solution;
        this.solution.remove(this.solution.size() - 1);
        this.cost = cost;
    }

    public void executeAlgorithm() {
        List<Move> bestMovesList = new ArrayList<>(50);
        Move bestMove;
        solution.stream();
        while(true) {
            bestMovesList.clear();
            bestMovesList.addAll(solution.stream().map(this::findBestMoveForVertex).collect(Collectors.toList()));
            bestMove = bestMovesList.stream().min((o1, o2) -> Integer.compare(o1.getDelta(), o2.getDelta())).get();
            if(bestMove.getDelta() < 0) {
                bestMove.doMove();
                cost += bestMove.getDelta();
            } else {
                break;
            }
        }
    }

    private Move findBestMoveForVertex(int vertexNumber) {
        Vertex currentVertex = vertices.get(vertexNumber);
        List<Move> minimalMoves = new ArrayList<>();

        //Find minimal vertices switch move for current vertex
        minimalMoves.add(vertices.stream()
                .filter(vertex -> !vertex.isInSolution())
                .map(vertex -> new SwitchVertices(currentVertex, vertex, solution))
                .min((o1, o2) -> Integer.compare(o1.getDelta(), o2.getDelta())).get());

        //Find minimal edges switch move for edge that start in current vertex

        return minimalMoves.stream().min((o1, o2) -> Integer.compare(o1.getDelta(), o2.getDelta())).get();
    }
}
