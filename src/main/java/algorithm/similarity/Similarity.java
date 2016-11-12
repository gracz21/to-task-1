package algorithm.similarity;

import java.util.List;
import java.util.Map;

/**
 * @author Kamil Walkowiak
 */
public interface Similarity {
    int checkSimilarity(List<Integer> solution1, List<Integer> solution2);
    void updateCommonSectionsMap(List<Integer> solution1, List<Integer> solution2, Map<Integer, Integer> commonSectionsMap);
}
