import algorithm.GredyCycle;
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

        GredyCycle gredyCycle = new GredyCycle(true, document.getGraph());
        gredyCycle.executeAlgorithm();

        System.out.println("Gredy Cycle");
        System.out.println("Min: " + gredyCycle.getResult().getMin());
        System.out.println("Avg: " + gredyCycle.getResult().getAvg());
        System.out.println("Max: " + gredyCycle.getResult().getMax());
        System.out.println(gredyCycle.getResult().getSolution());

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
    }
}
