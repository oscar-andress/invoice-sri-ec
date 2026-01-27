package demo.invoice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.invoice.dto.request.InvoiceAuthorizeRequest;
import demo.invoice.dto.request.IssueInvoiceRequest;
import demo.invoice.dto.request.SendInvoiceRequest;
import demo.invoice.dto.response.InvoiceAuthorizeResponse;
import demo.invoice.dto.response.IssueInvoiceResponse;
import demo.invoice.dto.response.SendInvoiceResponse;
import demo.invoice.service.InvoiceService;

@RestController
@RequestMapping("/api/v1/invoice")
@CrossOrigin

public class InvoiceController {
    
    private final InvoiceService invoiceService;

    InvoiceController(InvoiceService invoiceService){
        this.invoiceService = invoiceService;
    }

    @PostMapping("/issue")
    public ResponseEntity<IssueInvoiceResponse> issueInvoice(@RequestBody IssueInvoiceRequest request){
        return new ResponseEntity<> (invoiceService.issueInvoice(request), HttpStatus.CREATED);
    }

    @PutMapping("/send")
    public ResponseEntity<List<SendInvoiceResponse>> sendInvoices(@RequestBody List<SendInvoiceRequest> requests){
        return new ResponseEntity<>(invoiceService.sendInvoices(requests), HttpStatus.OK);
    }

    @PutMapping("/authorize")
    public ResponseEntity<List<InvoiceAuthorizeResponse>> authorizeInvoice(@RequestBody List<InvoiceAuthorizeRequest> request) {
        return new ResponseEntity<>(invoiceService.authorizeInvoices(request), HttpStatus.OK);
    }
}
