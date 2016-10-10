package model;

import adapter.AdapterDoubleToInt;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by inf109714 on 04.10.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Edge {
    @XmlTransient
    int startVertexNumber;

    @XmlValue
    int endVertexNumber;

    @XmlAttribute
    @XmlJavaTypeAdapter(AdapterDoubleToInt.class)
    Integer cost;

    public int getEndVertexNumber() {
        return endVertexNumber;
    }

    public Integer getCost() {
        return cost;
    }

    public int getStartVertexNumber() {
        return startVertexNumber;
    }

    public void setStartVertexNumber(int startVertexNumber) {
        this.startVertexNumber = startVertexNumber;
    }
}
