package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by inf109714 on 04.10.2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TravellingSalesmanProblemInstance {
    @XmlElement
    private Graph graph;
}
