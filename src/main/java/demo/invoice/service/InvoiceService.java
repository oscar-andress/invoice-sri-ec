package demo.invoice.service;

import demo.invoice.dto.request.InvoiceRequest;

public interface InvoiceService {
    String issueInvoice(InvoiceRequest request);
}
