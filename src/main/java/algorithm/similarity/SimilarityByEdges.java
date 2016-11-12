package algorithm.similarity;

import javafx.util.Pair;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Kamil Walkowiak
 */
public class SimilarityByEdges implements Similarity {
    @Override
    public int checkSimilarity(List<Integer> solution1, List<Integer> solution2) {
        List<Pair<Integer, Integer>> solutionEdges1 = mapToEdges(solution1);
        List<Pair<Integer, Integer>> solutionEdges2 = mapToEdges(solution2);

        return (int)solutionEdges1.stream().filter(solutionEdges2::contains).count();
    }

    @Override
    public void updateCommonSectionsMap(List<Integer> solution1, List<Integer> solution2, Map<Integer, Integer> commonSectionsMap) {
        List<Pair<Integer, Integer>> solutionEdges1 = mapToEdges(solution1);
        List<Pair<Integer, Integer>> solutionEdges2 = mapToEdges(solution2);

        solutionEdges1.stream()
                .filter(solutionEdges2::contains)
                .forEach(o -> commonSectionsMap.put(o.getKey(), o.getValue()));
    }

    private List<Pair<Integer, Integer>> mapToEdges(List<Integer> solution) {
        return IntStream.range(1, solution.size())
                .mapToObj(i -> new Pair<>(solution.get(i - 1), solution.get(i)))
                .collect(Collectors.toList());
    }
}
