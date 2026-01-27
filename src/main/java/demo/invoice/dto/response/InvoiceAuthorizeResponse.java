package demo.invoice.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class InvoiceAuthorizeResponse {

    private Long idInvoice;
    private String accessKey;    
    private String status;              
    private LocalDate authorizationDate;
}
