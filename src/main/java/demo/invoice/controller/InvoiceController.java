package demo.invoice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.invoice.dto.request.InvoiceRequest;
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
    public String issueInvoice(@RequestBody InvoiceRequest request){
        return invoiceService.issueInvoice(request);
    }

}
