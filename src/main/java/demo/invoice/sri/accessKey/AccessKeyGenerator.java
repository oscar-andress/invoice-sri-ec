package demo.invoice.sri.accessKey;

import java.time.LocalDateTime;

public interface AccessKeyGenerator {
    String generate(LocalDateTime issueDate,
                           String receipType, 
                           String ruc, 
                           String enviroment, 
                           String establishmentCode,
                           String emissionPointCode,
                           String nextInvoiceSequential,
                           String emissionType);
}
