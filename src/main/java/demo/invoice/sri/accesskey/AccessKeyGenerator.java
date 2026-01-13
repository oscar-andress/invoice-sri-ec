package demo.invoice.sri.accesskey;

import java.time.LocalDate;

public interface AccessKeyGenerator {
    String generate(LocalDate issueDate,
                           String receipType, 
                           String ruc, 
                           String enviroment, 
                           String establishmentCode,
                           String emissionPointCode,
                           String nextInvoiceSequential,
                           String emissionType);
}
