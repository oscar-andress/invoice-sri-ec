package demo.invoice.service;

import java.util.List;

import demo.invoice.dto.request.InvoiceAuthorizeRequest;
import demo.invoice.dto.request.IssueInvoiceRequest;
import demo.invoice.dto.request.SendInvoiceRequest;
import demo.invoice.dto.response.InvoiceAuthorizeResponse;
import demo.invoice.dto.response.IssueInvoiceResponse;
import demo.invoice.dto.response.SendInvoiceResponse;

public interface InvoiceService {
    IssueInvoiceResponse issueInvoice(IssueInvoiceRequest request);
    List<SendInvoiceResponse> sendInvoices(List<SendInvoiceRequest> requests);
    List<InvoiceAuthorizeResponse> authorizeInvoices(List<InvoiceAuthorizeRequest> requests);
}
