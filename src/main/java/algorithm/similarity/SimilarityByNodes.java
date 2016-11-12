package algorithm.similarity;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kamil Walkowiak
 */
public class SimilarityByNodes implements Similarity {
    @Override
    public int checkSimilarity(List<Integer> solution1, List<Integer> solution2) {
        return (int)solution1.stream().filter(solution2::contains).count();
    }

    @Override
    public void updateCommonSectionsMap(List<Integer> solution1, List<Integer> solution2, Map<Integer, Integer> commonSectionsMap) {
        solution1.stream().filter(solution2::contains).forEach(o -> {
            if(!commonSectionsMap.containsKey(o)) {
                commonSectionsMap.put(o, null);
            }
        });
    }
}
