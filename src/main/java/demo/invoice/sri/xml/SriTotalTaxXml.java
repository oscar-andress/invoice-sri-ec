package demo.invoice.sri.xml;

import java.math.BigDecimal;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)

public class SriTotalTaxXml {
    private String codigo;
    private String codigoPorcentaje;
    private BigDecimal baseImponible;
    private BigDecimal valor;
}
