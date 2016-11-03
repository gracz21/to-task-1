package algorithm.similarity;

import algorithm.LocalSearch;
import algorithm.Rand;
import model.Edge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by inf109714 on 25.10.2016.
 */
public class SimilarityExperiment {
    private Edge incidenceMatrix[][];
    private Rand rand;
    private LocalSearch localSearch;
    private List<List<Integer>> solutions;

    private SimilarityByNodes similarityByNodes;
    private SimilarityByEdges similarityByEdges;

    private List<Integer> similarityByNodesResults;
    private List<Integer> similarityByEdgesResults;
    private List<Integer> similarityByNodesWithBestResults;
    private List<Integer> similarityByEdgesWithBestResults;

    public SimilarityExperiment(Edge[][] incidenceMatrix) {
        this.incidenceMatrix = incidenceMatrix;
        rand = new Rand(incidenceMatrix);
        localSearch = new LocalSearch(incidenceMatrix);
        solutions = new ArrayList<>();

        similarityByNodes = new SimilarityByNodes();
        similarityByEdges = new SimilarityByEdges();

        similarityByNodesResults = new LinkedList<>();
        similarityByEdgesResults = new LinkedList<>();
        similarityByNodesWithBestResults = new LinkedList<>();
        similarityByEdgesWithBestResults = new LinkedList<>();
    }

    public void execute() {

    }
}
