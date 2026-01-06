package demo.invoice.service;

public interface InvoiceSequentialService {
    String nextInvoiceSequential(String ruc, String establishment, String emissionPoint, String documentType);
}
