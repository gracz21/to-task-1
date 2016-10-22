import algorithm.GreedyCycle;
import algorithm.MultipleStartLocalSearch;
import algorithm.NN;
import algorithm.Rand;
import model.Document;
import model.Edge;
import model.Vertex;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public class Main {
    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(Document.class);
        Unmarshaller um = context.createUnmarshaller();
        Document document = (Document) um.unmarshal(new FileReader("kroA100.xml"));

        Edge incidenceMatrix[][] = new Edge[100][100];
        List<Vertex> vertices = document.getGraph().getVertices();
        for(int i = 0; i < vertices.size(); i++) {
            int currentIndex = i;
            vertices.get(i).getEdges().forEach(edge ->{
                edge.setStartVertexNumber(currentIndex);
                incidenceMatrix[currentIndex][edge.getEndVertexNumber()] = edge;
            });
        }

        NN nn = new NN(true, incidenceMatrix);
        nn.executeAlgorithm();
        System.out.println("NN");
        nn.printResults();

        NN graspNn = new NN(false, incidenceMatrix);
        graspNn.executeAlgorithm();
        System.out.println("\nGRASP NN");
        graspNn.printResults();

        GreedyCycle greedyCycle = new GreedyCycle(true, incidenceMatrix);
        greedyCycle.executeAlgorithm();
        System.out.println("\nGreedy Cycle");
        greedyCycle.printResults();

        GreedyCycle graspGreedyCycle = new GreedyCycle(false, incidenceMatrix);
        graspGreedyCycle.executeAlgorithm();
        System.out.println("\nGRASP Greedy Cycle");
        graspGreedyCycle.printResults();

        Rand rand = new Rand(incidenceMatrix);
        rand.executeAlgorithm();
        System.out.println("\nRandom");
        rand.printResults();

        MultipleStartLocalSearch mlsl = new MultipleStartLocalSearch(new NN(false, incidenceMatrix));
        mlsl.executeAlgorithm();
        System.out.println("\nMultiple Start Local Search");
        mlsl.printResults();
    }
}
