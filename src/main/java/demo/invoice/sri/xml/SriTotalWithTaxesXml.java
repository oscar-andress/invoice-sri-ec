package demo.invoice.sri.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "totalImpuesto"
})
@Getter
@Setter

public class SriTotalWithTaxesXml {

    @XmlElement(name = "totalImpuesto", required = true)    
    protected List<SriTotalTaxXml> totalImpuesto;

    public List<SriTotalTaxXml> getTotalImpuesto() {
        if (totalImpuesto == null) {
            totalImpuesto = new ArrayList<>();
        }
        return this.totalImpuesto;
    }
}
