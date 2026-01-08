package demo.invoice.domain.context;

import demo.invoice.entity.Issuer;
import demo.invoice.entity.IssuerConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class InvoiceContext {
    private final Issuer issuer;
    private final IssuerConfig issuerConfig;
    private final String nextInvoiceSequential;
    private final String accessKey;
}
