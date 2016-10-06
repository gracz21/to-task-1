package model;

import adapter.AdapterDoubleToInt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by inf109714 on 04.10.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Edge {
    @XmlValue
    int number;
    @XmlAttribute
    @XmlJavaTypeAdapter(AdapterDoubleToInt.class)
    Integer cost;
}
