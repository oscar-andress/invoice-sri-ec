package demo.invoice.domain.calculator;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class InvoiceTaxTotal {
    private String taxCode;
    private String taxPercentageCode;
    private BigDecimal taxableBase;
    private BigDecimal taxValue;
}
