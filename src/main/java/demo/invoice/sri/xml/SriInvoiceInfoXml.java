package demo.invoice.sri.xml;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
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
    "fechaEmision",
    "dirEstablecimiento",
    "obligadoContabilidad",
    "tipoIdentificacionComprador",
    "razonSocialComprador",
    "identificacionComprador",
    "totalSinImpuestos",
    "totalDescuento",
    "totalConImpuestos",
    "propina",
    "importeTotal",
    "moneda"
})
public class SriInvoiceInfoXml {
    private String fechaEmision;
    private String dirEstablecimiento;
    private String obligadoContabilidad;
    private String tipoIdentificacionComprador;
    private String razonSocialComprador;
    private String identificacionComprador;
    private BigDecimal totalSinImpuestos;
    private BigDecimal totalDescuento;

    @XmlElementWrapper(name = "totalConImpuestos")
    @XmlElement(name = "totalImpuesto")
    private List<SriTotalTaxXml> totalConImpuestos = new ArrayList<>();

    private BigDecimal propina;
    private BigDecimal importeTotal;
    private String moneda;

}
