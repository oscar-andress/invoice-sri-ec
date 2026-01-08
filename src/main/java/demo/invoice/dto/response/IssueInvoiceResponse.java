package demo.invoice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class IssueInvoiceResponse {

    private Long idInvoice;
    private String accessKey;    
    private String sequential;         
    private String status;              
    private LocalDate issueDate;
    private BigDecimal totalAmount;

}
