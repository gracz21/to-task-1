import model.TravellingSalesmanProblemInstance;

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
        JAXBContext context = JAXBContext.newInstance(TravellingSalesmanProblemInstance.class);
        Unmarshaller um = context.createUnmarshaller();
        TravellingSalesmanProblemInstance travellingSalesmanProblemInstance = (TravellingSalesmanProblemInstance) um.unmarshal(new FileReader("kroA100.xml"));

        String sdf;
        sdf = "dsdf";
    }
}
