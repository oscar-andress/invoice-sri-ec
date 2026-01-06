package demo.invoice.sri.xml;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SriTaxesXml {
    @XmlElement(name = "impuesto", required = true)
    private List<SriTaxXml> impuesto = new ArrayList<>();

    public List<SriTaxXml> getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(List<SriTaxXml> impuesto) {
        this.impuesto = impuesto;
    }

}
