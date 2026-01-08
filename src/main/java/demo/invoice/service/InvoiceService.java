package demo.invoice.service;

import demo.invoice.dto.request.IssueInvoiceRequest;
import demo.invoice.dto.response.IssueInvoiceResponse;

public interface InvoiceService {
    IssueInvoiceResponse issueInvoice(IssueInvoiceRequest request);
}
