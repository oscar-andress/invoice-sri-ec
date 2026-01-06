package demo.invoice.sri.xml;

import java.math.BigDecimal;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "codigoPrincipal",
    "descripcion",
    "cantidad",
    "precioUnitario",
    "precioSinSubsidio",
    "descuento",
    "precioTotalSinImpuesto",
    "impuestos"
})
public class SriDetailInvoiceXml {
    private String codigoPrincipal;
    private String descripcion;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal precioSinSubsidio;
    private BigDecimal descuento;
    private BigDecimal precioTotalSinImpuesto;

    @XmlElement(name = "impuestos")
    private SriTaxesXml impuestos;
}
