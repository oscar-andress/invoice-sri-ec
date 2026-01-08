package demo.invoice.dto.request;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class IssueInvoiceRequest {
    // Buyer information
    private String buyerIdentification;
    private String buyerName;
    private String buyerIdentificationType;
    private String buyerAddress; 

    // Invoice details
    private List<IssueInvoiceDetailRequest> details;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IssueInvoiceDetailRequest {
        private String description;
        private BigDecimal quantity;
        private BigDecimal unitPrice;
        private BigDecimal discount;
        private String taxCode;
        private String taxPercentageCode;
        private BigDecimal taxPercentage; 
    }
}
