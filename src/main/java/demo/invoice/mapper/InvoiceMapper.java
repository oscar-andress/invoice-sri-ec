package demo.invoice.mapper;

import org.springframework.stereotype.Component;

import demo.invoice.dto.response.IssueInvoiceResponse;
import demo.invoice.entity.Invoice;

@Component
public class InvoiceMapper {
    
    public IssueInvoiceResponse toIssueResponse(Invoice entity){
        IssueInvoiceResponse response = new IssueInvoiceResponse();
        response.setAccessKey(entity.getAccessKey());
        response.setIdInvoice(entity.getIdInvoice());
        response.setIssueDate(entity.getIssueDate());
        response.setSequential(entity.getSequential());
        response.setStatus(entity.getStatus());
        response.setTotalAmount(entity.getTotalAmount());
        return response;
    }
}
