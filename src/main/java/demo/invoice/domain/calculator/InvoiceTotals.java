package demo.invoice.domain.calculator;

import java.math.BigDecimal;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class InvoiceTotals {
    private BigDecimal totalWithoutTaxes;
    private BigDecimal totalDiscount;
    private BigDecimal totalTaxes;
    private BigDecimal grandTotal;
    private Map<String, InvoiceTaxTotal> taxTotals;
}
