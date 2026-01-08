package demo.invoice.domain.calculator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import demo.invoice.dto.request.IssueInvoiceRequest.IssueInvoiceDetailRequest;

@Component
public class InvoiceCalculator {

    public InvoiceTotals calculate(List<IssueInvoiceDetailRequest> details) {

        BigDecimal totalWithoutTaxes = BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;
        BigDecimal totalTaxes = BigDecimal.ZERO;

        Map<String, InvoiceTaxTotal> taxMap = new HashMap<>();

        for (IssueInvoiceDetailRequest detail : details) {

            BigDecimal quantity = detail.getQuantity();
            BigDecimal unitPrice = detail.getUnitPrice();
            BigDecimal discount = detail.getDiscount() != null
                    ? detail.getDiscount()
                    : BigDecimal.ZERO;

            // Base imponible por línea
            BigDecimal lineBase = quantity
                    .multiply(unitPrice)
                    .subtract(discount);

            totalWithoutTaxes = totalWithoutTaxes.add(lineBase);
            totalDiscount = totalDiscount.add(discount);

            // ===== TAX =====
            BigDecimal taxRate = detail.getTaxPercentage()
                    .divide(BigDecimal.valueOf(100));

            BigDecimal taxValue = lineBase.multiply(taxRate);

            totalTaxes = totalTaxes.add(taxValue);

            String taxKey = detail.getTaxCode() + "-" + detail.getTaxPercentageCode();

            InvoiceTaxTotal taxTotal = taxMap.computeIfAbsent(
                taxKey,
                k -> new InvoiceTaxTotal(
                        detail.getTaxCode(),
                        detail.getTaxPercentageCode(),
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                )
            );

            taxTotal.setTaxableBase(
                taxTotal.getTaxableBase().add(lineBase)
            );

            taxTotal.setTaxValue(
                taxTotal.getTaxValue().add(taxValue)
            );
        }

        InvoiceTotals totals = new InvoiceTotals();
        totals.setTotalWithoutTaxes(totalWithoutTaxes);
        totals.setTotalDiscount(totalDiscount);
        totals.setTotalTaxes(totalTaxes);
        totals.setGrandTotal(totalWithoutTaxes.add(totalTaxes));
        totals.setTaxTotals(taxMap);

        return totals;
    
    }
}
