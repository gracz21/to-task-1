import algorithm.NN;
import model.Document;

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
        document.getGraph().getVertices().forEach(vertex -> vertex.getEdges()
                .sort((o1, o2) -> Integer.compare(o1.getCost(), o2.getCost())));

        NN nn = new NN(document.getGraph());
        nn.executeAlgorithm();

        System.out.println("NN");
        System.out.println("Min: " + nn.getResult().getMin());
        System.out.println("Avg: " + nn.getResult().getAvg());
        System.out.println("Max: " + nn.getResult().getMax());
        System.out.println(nn.getResult().getSolution());
    }
}
