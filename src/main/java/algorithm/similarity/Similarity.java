package algorithm.similarity;

import model.Edge;

import java.util.List;

/**
 * Created by inf109714 on 25.10.2016.
 */
public interface Similarity {
    int checkSimilarity(List<Integer> solution1, List<Integer> solution2);
}
