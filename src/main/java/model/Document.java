package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by inf109714 on 04.10.2016.
 */
@XmlRootElement(name = "travellingSalesmanProblemInstance")
@XmlAccessorType(XmlAccessType.FIELD)
public class Document {
    @XmlElement
    private Graph graph;

    public Graph getGraph() {
        return graph;
    }
}
