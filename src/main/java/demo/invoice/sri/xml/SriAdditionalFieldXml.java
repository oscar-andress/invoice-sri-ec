package demo.invoice.sri.xml;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD)
public class SriAdditionalFieldXml {
    @XmlAttribute
    private String nombre;

    @XmlValue
    private String valor;
}

