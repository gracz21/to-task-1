package adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by inf109714 on 04.10.2016.
 */
public class AdapterDoubleToInt extends XmlAdapter<Double, Integer> {
    @Override
    public Integer unmarshal(Double v) throws Exception {
        return (int)(v + 0.5);
    }

    @Override
    public Double marshal(Integer v) throws Exception {
        return null;
    }
}
