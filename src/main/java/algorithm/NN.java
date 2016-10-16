package algorithm;

import com.sun.xml.internal.ws.util.StreamUtils;
import model.Edge;
import model.Vertex;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by inf109714 on 04.10.2016.
 */
public class NN extends Algorithm {
    private boolean isDeterministic;
    private LocalSearch localSearch;

    public NN(boolean isDeterministic, Edge incidenceMatrix[][]) {
        super(incidenceMatrix);
        this.isDeterministic = isDeterministic;
        this.localSearch = new LocalSearch(incidenceMatrix);
    }

    public void executeAlgorithm() {
        int currentSolutionValue;
        List<Integer> currentSolution = new ArrayList<>();
        for(int i = 0; i < incidenceMatrix.length; i++) {
            currentSolutionValue = 0;
            currentSolution.clear();
            currentSolution.add(i);

            while(currentSolution.size() < 50) {
                nextIteration(currentSolution);
            }
            currentSolution.add(i);

            for(int j = 0; j + 1 < currentSolution.size(); j++) {
                currentSolutionValue += this.incidenceMatrix[currentSolution.get(j)][currentSolution.get(j+1)].getCost();
            }

            this.result.updateResult(currentSolution, currentSolutionValue);
            localSearch.setSolution(currentSolution);
            localSearch.setCost(currentSolutionValue);
            localSearch.executeAlgorithm();
        }

        this.resultAfterLocalSearch = localSearch.getResult();
    }

    @Override
    protected void nextIteration(List<Integer> currentSolution) {
        int currentVertex =  currentSolution.get(currentSolution.size() - 1);
        Edge topEdges[] = new Edge[3];
        int selectedVertex = IntStream.range(0, this.incidenceMatrix[currentVertex].length)
                .filter(value -> !currentSolution.contains(value))
                .mapToObj(operand -> this.incidenceMatrix[currentVertex][operand])
                .min((o1, o2) -> Integer.compare(o1.getCost(), o2.getCost()))
                .get().getEndVertexNumber();

        currentSolution.add(selectedVertex);
    }
}
