package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by inf109714 on 04.10.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Graph {
    @XmlElement(name="vertex")
    List<Vertex> vertices;

    public List<Vertex> getVertices() {
        return vertices;
    }
}
