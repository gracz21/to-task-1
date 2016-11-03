package algorithm.similarity;

import algorithm.LocalSearch;
import algorithm.Rand;
import javafx.util.Pair;
import model.Edge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kamil Walkowiak
 */
public class SimilarityExperiment {
    private Rand rand;
    private List<Pair<List<Integer>, Integer>> solutions;
    private Pair<List<Integer>, Integer> bestSolution;

    private SimilarityByNodes similarityByNodesAlgorithm;
    private SimilarityByEdges similarityByEdgesAlgorithm;

    private List<Pair<Integer, Double>> similarityByNodesResults;
    private List<Pair<Integer, Double>> similarityByEdgesResults;
    private List<Pair<Integer, Integer>> similarityByNodesWithBestResults;
    private List<Pair<Integer, Integer>> similarityByEdgesWithBestResults;

    public SimilarityExperiment(Edge[][] incidenceMatrix) {
        rand = new Rand(incidenceMatrix);
        solutions = new ArrayList<>();
        bestSolution = new Pair<>(null, Integer.MAX_VALUE);

        similarityByNodesAlgorithm = new SimilarityByNodes();
        similarityByEdgesAlgorithm = new SimilarityByEdges();

        similarityByNodesResults = new LinkedList<>();
        similarityByEdgesResults = new LinkedList<>();
        similarityByNodesWithBestResults = new LinkedList<>();
        similarityByEdgesWithBestResults = new LinkedList<>();
    }

    public void execute() {
        for(int i = 0; i < 1000; i++) {
            Pair<List<Integer>, Integer> currentSolution = rand.getSingleResultAfterLocalSearch();
            solutions.add(currentSolution);

            if(bestSolution.getValue() > currentSolution.getValue()) {
                bestSolution = currentSolution;
            }
        }

        System.out.println("Finished solutions generating");

        for(Pair<List<Integer>, Integer> solution: solutions) {
            int similarityByNodes = 0, similarityByEdges = 0, similarityByNodesWithBest = 0, similarityByEdgesWithBest = 0;

            for(Pair<List<Integer>, Integer> otherSolution: solutions.stream().filter(o -> o != solution).collect(Collectors.toList())) {
                similarityByNodes += similarityByNodesAlgorithm.checkSimilarity(solution.getKey(), otherSolution.getKey());
                similarityByEdges += similarityByEdgesAlgorithm.checkSimilarity(solution.getKey(), otherSolution.getKey());
            }

            if(solution != bestSolution) {
                similarityByNodesWithBest = similarityByNodesAlgorithm.checkSimilarity(solution.getKey(), bestSolution.getKey());
                similarityByEdgesWithBest = similarityByEdgesAlgorithm.checkSimilarity(solution.getKey(), bestSolution.getKey());

                similarityByNodesWithBestResults.add(new Pair<>(solution.getValue(), similarityByNodesWithBest));
                similarityByEdgesWithBestResults.add(new Pair<>(solution.getValue(), similarityByEdgesWithBest));
            }

            similarityByNodesResults.add(new Pair<>(solution.getValue(), similarityByNodes/999.0));
            similarityByEdgesResults.add(new Pair<>(solution.getValue(), similarityByEdges/999.0));

//            System.out.println();
//            System.out.println(similarityByNodes/999);
//            System.out.println(similarityByEdges/999);
//            System.out.println(similarityByNodesWithBest);
//            System.out.println(similarityByEdgesWithBest);
        }
    }

    public void printResults() {
        System.out.println("Similarity by nodes:");
        similarityByNodesResults.forEach(o -> System.out.println(o.getKey() + "\t" + o.getValue()));

        System.out.println("\nSimilarity by edges:");
        similarityByEdgesResults.forEach(o -> System.out.println(o.getKey() + "\t" + o.getValue()));

        System.out.println("\nSimilarity by nodes with best:");
        similarityByNodesWithBestResults.forEach(o -> System.out.println(o.getKey() + "\t" + o.getValue()));

        System.out.println("\nSimilarity by edges with best:");
        similarityByEdgesWithBestResults.forEach(o -> System.out.println(o.getKey() + "\t" + o.getValue()));
    }
}
