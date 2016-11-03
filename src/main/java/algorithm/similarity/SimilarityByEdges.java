package algorithm.similarity;

import javafx.util.Pair;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Kamil Walkowiak
 */
public class SimilarityByEdges implements Similarity {
    @Override
    public int checkSimilarity(List<Integer> solution1, List<Integer> solution2) {
        List<Pair> solutionEdges1 = IntStream.range(1, solution1.size())
                .mapToObj(i -> new Pair<>(solution1.get(i - 1), solution1.get(i)))
                .collect(Collectors.toList());
        List<Pair> solutionEdges2 = IntStream.range(1, solution2.size())
                .mapToObj(i -> new Pair<>(solution2.get(i - 1), solution2.get(i)))
                .collect(Collectors.toList());

        return (int)solutionEdges1.stream().filter(solutionEdges2::contains).count();
    }
}
