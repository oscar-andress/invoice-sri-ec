package demo.invoice.service.domain.factory;

import java.math.BigDecimal;
import java.time.LocalDate;

import demo.invoice.entity.Invoice;
import demo.invoice.enumeration.InvoiceStatus;

public final class InvoiceTestFactory {

    public static Invoice issuedInvoice() {
        Invoice invoice = new Invoice();

        invoice.setIdIssuer(1);
        invoice.setBuyerIdType("05");
        invoice.setEnvironment("1");
        invoice.setAccessKey("TEST-ACCESS-KEY");
        invoice.setSequential("000000001");

        invoice.setBuyerIdentification("9999999999");
        invoice.setBuyerName("TEST BUYER");

        invoice.setIssueDate(LocalDate.now());
        invoice.setTotalWithoutTaxes(BigDecimal.TEN);
        invoice.setTotalTaxAmount(BigDecimal.ZERO);
        invoice.setTotalAmount(BigDecimal.TEN);

        invoice.setStatus(InvoiceStatus.ISSUED.toString());
        invoice.setUnsignedXml("<xml>unsigned</xml>");
        invoice.setSignedXml("<xml>signed</xml>");

        return invoice;
    }

}
