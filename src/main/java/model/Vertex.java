package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * Created by inf109714 on 04.10.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Vertex {
    @XmlTransient
    boolean isInSolution;

    @XmlTransient
    int number;

    @XmlElement(name="edge")
    List<Edge> edges;

    public Vertex() {
        isInSolution = false;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setInSolution(boolean inSolution) {
        isInSolution = inSolution;
    }

    public boolean isInSolution() {
        return isInSolution;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
