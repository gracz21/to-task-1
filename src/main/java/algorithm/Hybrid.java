package algorithm;

import algorithm.similarity.SimilarityByEdges;
import algorithm.similarity.SimilarityByNodes;
import javafx.util.Pair;
import model.Edge;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Kamil Walkowiak
 */
public class Hybrid {
    private Edge[][] incidenceMatrix;
    private Rand rand;
    private Random random;
    private LocalSearch localSearch;
    private SimilarityByNodes similarityByNodes;
    private SimilarityByEdges similarityByEdges;
    private long stopCondition;
    private Map<Integer, List<Integer>> population;
    private Result result;
    private List<Long> localSearchTimes;
    private List<Integer> localSearchCosts;

    public Hybrid(Edge[][] incidenceMatrix, long stopCondition) {
        this.incidenceMatrix = incidenceMatrix;
        this.rand = new Rand(incidenceMatrix);
        this.random = new Random();
        this.population = new HashMap<>();
        this.similarityByNodes = new SimilarityByNodes();
        this.similarityByEdges = new SimilarityByEdges();
        this.localSearch = new LocalSearch(incidenceMatrix);
        this.stopCondition = stopCondition;
        this.result = new Result();
        localSearchTimes = new LinkedList<>();
        localSearchCosts = new LinkedList<>();
    }

    public void printResultsToFile() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("hybrid_out.txt", "UTF-8");
        writer.write("Min: " + this.result.getMin() + "\n");
        writer.write("Avg: " + this.result.getAvg() + "\n");
        writer.write("Max: " + this.result.getMax() + "\n");
        writer.write("LS times\n");
        for(int i = 0; i < localSearchTimes.size(); i++) {
            writer.write(i + "\t" + localSearchTimes.get(i)/1000000 + "\n");
        }
        writer.write("LS costs\n");
        for (int i = 0; i < localSearchCosts.size(); i++) {
            writer.write(i + "\t" + localSearchCosts.get(i) + "\n");
        }
        writer.close();
    }

    public void executeAlgorithm() {
        long estimatedTime;
        List<List<Integer>> parents = new ArrayList<>(2);

        for(int i = 0; i < 10; i++) {
            estimatedTime = 0;
            long startTime = System.nanoTime();
            this.population.clear();

            while(population.size() < 20) {
                Pair<List<Integer>, Integer> solution = rand.getSingleResultAfterLocalSearch();
                if(!population.containsKey(solution.getValue())) {
                    population.put(solution.getValue(), solution.getKey());
                }
            }

            while(estimatedTime <= this.stopCondition) {
                findParents(parents);

                Pair<Integer, List<Integer>> child = recombination(parents);

                executeLocalSearch(child);
                if(i == 0) {
                    updateLocalSearchResults();
                }
                updatePopulation();

                estimatedTime = System.nanoTime() - startTime;
            }

            int minCost = population.keySet().stream().min(Integer::compareTo).get();
            System.out.println("Iter: " + i + " time: " + estimatedTime + " min: " + minCost);
            result.updateResult(population.get(minCost), minCost);
            result.updateTimeResult(estimatedTime);
        }
    }

    private void findParents(List<List<Integer>> parents) {
        parents.clear();
        List<Integer> availableParentsIndexes = IntStream.range(0, population.size())
                .boxed()
                .collect(Collectors.toList());
        int selectedParentIndex = availableParentsIndexes.get(random.nextInt(availableParentsIndexes.size()));
        parents.add((List<Integer>)population.values().toArray()[selectedParentIndex]);

        availableParentsIndexes = availableParentsIndexes.stream()
                .filter(idx -> idx != selectedParentIndex)
                .collect(Collectors.toList());
        parents.add((List<Integer>)population.values().toArray()[
                availableParentsIndexes.get(random.nextInt(availableParentsIndexes.size()))]);
    }

    private Pair<Integer, List<Integer>> recombination(List<List<Integer>> parents) {
        Random random = new Random();

        Map<Integer, Integer> commonSectionsMap = new HashMap<>();
        List<List<Integer>> sectionsList = new LinkedList<>();
        List<Integer> currentSection;

        similarityByEdges.updateCommonSectionsMap(parents.get(0), parents.get(1), commonSectionsMap);
        similarityByNodes.updateCommonSectionsMap(parents.get(0), parents.get(1), commonSectionsMap);

        Collection<Integer> edgesEnds = commonSectionsMap.values();
        for(Map.Entry<Integer, Integer> entry: commonSectionsMap.entrySet()) {
            if(!edgesEnds.contains(entry.getKey())) {
                //start new section
                currentSection = new LinkedList<>();
                currentSection.add(entry.getKey());

                //not single vertex but edge
                if (entry.getValue() != null) {
                    currentSection.add(entry.getValue());
                }

                //follow section til its end
                Integer currentVertex = entry.getValue();
                while(commonSectionsMap.containsKey(currentVertex)) {
                    currentVertex = commonSectionsMap.get(currentVertex);
                    if(currentVertex != null) {
                        currentSection.add(currentVertex);
                    }
                }

                //when reached end of section add to list of sections
                sectionsList.add(currentSection);
            }
        }

        List<Integer> includedVertices = sectionsList.stream().flatMap(List::stream).collect(Collectors.toList());
        while(includedVertices.size() < 50) {
            List<Integer> availableVertices = IntStream.range(0, incidenceMatrix.length)
                    .boxed()
                    .filter(i -> !includedVertices.contains(i))
                    .collect(Collectors.toList());

            int selectedVertex = availableVertices.get(random.nextInt(availableVertices.size()));
            includedVertices.add(selectedVertex);

            currentSection = new LinkedList<>();
            currentSection.add(selectedVertex);
            sectionsList.add(currentSection);
        }

        List<Integer> usedSections = new LinkedList<>();
        List<Integer> childSolution = new LinkedList<>();
        while(usedSections.size() < sectionsList.size()) {
            List<Integer> availableSectionsIndexes = IntStream.range(0, sectionsList.size())
                    .boxed()
                    .filter(i -> !usedSections.contains(i))
                    .collect(Collectors.toList());

            int selectedSectionIndex = availableSectionsIndexes.get(random.nextInt(availableSectionsIndexes.size()));
            childSolution.addAll(sectionsList.get(selectedSectionIndex));
            usedSections.add(selectedSectionIndex);
        }
        childSolution.add(childSolution.get(0));

        int childSolutionValue = 0;
        for(int i = 0; i + 1 < childSolution.size(); i++) {
            childSolutionValue += this.incidenceMatrix[childSolution.get(i)][childSolution.get(i + 1)].getCost();
        }

        return new Pair<>(childSolutionValue, childSolution);
    }

    private void executeLocalSearch(Pair<Integer, List<Integer>> child) {
        localSearch.setSolution(child.getValue());
        localSearch.setCost(child.getKey());
        localSearch.executeAlgorithm();
    }

    private void updateLocalSearchResults() {
        localSearchCosts.add(localSearch.getCost());
        localSearchTimes.add(localSearch.getEstimatedTime());
    }

    private void updatePopulation() {
        if(!population.containsKey(localSearch.getCost())) {
            int maxCost = population.keySet().stream().max(Integer::compareTo).get();
            if(localSearch.getCost() < maxCost) {
                population.remove(maxCost);
                population.put(localSearch.getCost(), localSearch.getSolution());
            }
        }
    }
}
