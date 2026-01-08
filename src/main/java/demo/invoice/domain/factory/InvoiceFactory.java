package demo.invoice.domain.factory;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import demo.invoice.domain.calculator.InvoiceTotals;
import demo.invoice.domain.context.InvoiceContext;
import demo.invoice.dto.request.IssueInvoiceRequest;
import demo.invoice.entity.Invoice;
import demo.invoice.enumeration.InvoiceStatus;

@Component
public class InvoiceFactory {
    public Invoice create(
            IssueInvoiceRequest request,
            InvoiceContext context,
            String unsignedXml,
            String signedXml,
            InvoiceTotals invoiceTotals, 
            String accessKey,
            String sequential) {

        Invoice invoice = new Invoice();
        invoice.setIdIssuer(context.getIssuer().getIdIssuer());
        invoice.setBuyerIdType(request.getBuyerIdentificationType());
        invoice.setEnvironment(context.getIssuerConfig().getEnvironment());
        invoice.setAccessKey(accessKey);
        invoice.setSequential(sequential);

        invoice.setBuyerIdentification(request.getBuyerIdentification());
        invoice.setBuyerName(request.getBuyerName());

        invoice.setIssueDate(LocalDate.now());
        invoice.setTotalWithoutTaxes(invoiceTotals.getTotalWithoutTaxes());
        invoice.setTotalTaxAmount(invoiceTotals.getTotalTaxes());
        invoice.setTotalAmount(invoiceTotals.getGrandTotal());

        invoice.setStatus(InvoiceStatus.ISSUED.toString());

        invoice.setUnsignedXml(unsignedXml);
        invoice.setSignedXml(signedXml);

        return invoice;
    }
}
