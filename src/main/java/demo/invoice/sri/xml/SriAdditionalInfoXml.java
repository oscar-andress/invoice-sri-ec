package demo.invoice.sri.xml;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SriAdditionalInfoXml {
    
    @XmlElement(name = "campoAdicional")
    private List<SriAdditionalFieldXml> campos = new ArrayList<>();
}