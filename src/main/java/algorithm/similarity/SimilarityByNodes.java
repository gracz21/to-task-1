package algorithm.similarity;

import model.Edge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by inf109714 on 25.10.2016.
 */
public class SimilarityByNodes implements Similarity {
    @Override
    public int checkSimilarity(List<Integer> solution1, List<Integer> solution2) {
        return (int)solution1.stream().filter(solution2::contains).count();
    }
}
