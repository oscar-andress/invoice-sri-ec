package demo.invoice.sri.xml;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@XmlRootElement(name = "factura")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "infoTributaria",
    "infoFactura",
    "detalles",
    "infoAdicional"
})
public class SriInvoiceXml {

    @XmlAttribute(name = "id")
    private String id = "comprobante";

    @XmlAttribute
    private String version = "1.1.0";

    @XmlElement(required = true)
    private SriTributaryInfoXml infoTributaria;

    @XmlElement(required = true)
    private SriInvoiceInfoXml infoFactura;

    @XmlElementWrapper(name = "detalles")
    @XmlElement(name = "detalle", required = true)
    private List<SriDetailInvoiceXml> detalles = new ArrayList<>();

    @XmlElement(name = "infoAdicional")
    private SriAdditionalInfoXml infoAdicional;
}