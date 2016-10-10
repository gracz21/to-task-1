import algorithm.GreedyCycle;
import algorithm.NN;
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
            vertex.getEdges().forEach(edge -> edge.setStartVertexNumber(document.getGraph().getVertices().indexOf(vertex)));
        }

        NN nn = new NN(true, document.getGraph());
        nn.executeAlgorithm();

        System.out.println("NN");
        System.out.println("Min: " + nn.getResult().getMin());
        System.out.println("Avg: " + nn.getResult().getAvg());
        System.out.println("Max: " + nn.getResult().getMax());
        System.out.println(nn.getResult().getSolution());

        NN graspNn = new NN(false, document.getGraph());
        graspNn.executeAlgorithm();

        System.out.println("GRASP NN");
        System.out.println("Min: " + graspNn.getResult().getMin());
        System.out.println("Avg: " + graspNn.getResult().getAvg());
        System.out.println("Max: " + graspNn.getResult().getMax());
        System.out.println(graspNn.getResult().getSolution());

        GreedyCycle greedyCycle = new GreedyCycle(true, document.getGraph());
        greedyCycle.executeAlgorithm();

        System.out.println("Greedy Cycle");
        System.out.println("Min: " + greedyCycle.getResult().getMin());
        System.out.println("Avg: " + greedyCycle.getResult().getAvg());
        System.out.println("Max: " + greedyCycle.getResult().getMax());
        System.out.println(greedyCycle.getResult().getSolution());

        GreedyCycle graspGreedyCycle = new GreedyCycle(false, document.getGraph());
        graspGreedyCycle.executeAlgorithm();

        System.out.println("GRASP Greedy Cycle");
        System.out.println("Min: " + graspGreedyCycle.getResult().getMin());
        System.out.println("Avg: " + graspGreedyCycle.getResult().getAvg());
        System.out.println("Max: " + graspGreedyCycle.getResult().getMax());
        System.out.println(graspGreedyCycle.getResult().getSolution());
    }
}
