package algorithm;

import model.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by inf109714 on 11.10.2016.
 */
public class LocalSearch {
    private List<Vertex> vertices;
    private List<Integer> solution;
    private int cost;

    public LocalSearch(List<Vertex> vertices, List<Integer> solution, int cost) {
        this.vertices = vertices;
        this.solution = solution;
        this.cost = cost;
    }

    public void executeAlgorithm() {
        List<Integer> deltaList = new ArrayList<>(50);
        //Collections.reverse();
        solution.stream();
    }
}
