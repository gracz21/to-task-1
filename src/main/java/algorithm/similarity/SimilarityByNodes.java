package algorithm.similarity;

import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public class SimilarityByNodes implements Similarity {
    @Override
    public int checkSimilarity(List<Integer> solution1, List<Integer> solution2) {
        return (int)solution1.stream().filter(solution2::contains).count();
    }
}
