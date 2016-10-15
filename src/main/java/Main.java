import algorithm.GreedyCycle;
import algorithm.NN;
import algorithm.Rand;
import model.Document;
import model.Vertex;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by inf109714 on 04.10.2016.
 */
public class Main {
    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(Document.class);
        Unmarshaller um = context.createUnmarshaller();
        Document document = (Document) um.unmarshal(new FileReader("kroA100.xml"));
        for(Vertex vertex: document.getGraph().getVertices()) {
            int vertexNumber = document.getGraph().getVertices().indexOf(vertex);
            vertex.setNumber(vertexNumber);
            vertex.getEdges().forEach(edge -> edge.setStartVertexNumber(vertexNumber));
        }

        NN nn = new NN(true, document.getGraph().getVertices());
        nn.executeAlgorithm();
        System.out.println("NN");
        nn.printResults();

        NN graspNn = new NN(false, document.getGraph().getVertices());
        graspNn.executeAlgorithm();
        System.out.println("\nGRASP NN");
        graspNn.printResults();

        GreedyCycle greedyCycle = new GreedyCycle(true, document.getGraph().getVertices());
        greedyCycle.executeAlgorithm();
        System.out.println("\nGreedy Cycle");
        greedyCycle.printResults();

        GreedyCycle graspGreedyCycle = new GreedyCycle(false, document.getGraph().getVertices());
        graspGreedyCycle.executeAlgorithm();
        System.out.println("\nGRASP Greedy Cycle");
        graspGreedyCycle.printResults();

        Rand rand = new Rand(document.getGraph().getVertices());
        rand.executeAlgorithm();
        System.out.println("\nRandom");
        rand.printResults();
    }
}
