package demo.invoice.service.impl;

import org.springframework.stereotype.Service;

import demo.invoice.entity.InvoiceSequential;
import demo.invoice.repository.InvoiceSequentialRepository;
import demo.invoice.service.InvoiceSequentialService;

@Service
public class InvoiceSequentialServiceImpl implements InvoiceSequentialService{

    private final InvoiceSequentialRepository invoiceSequentialRepository;

    InvoiceSequentialServiceImpl(InvoiceSequentialRepository invoiceSequentialRepository){
        this.invoiceSequentialRepository = invoiceSequentialRepository;
    }

    @Override
    public String nextInvoiceSequential(String ruc, String establishment, String emissionPoint, String documentType) {
        // Find existing sequential
        InvoiceSequential invoiceSequential = invoiceSequentialRepository
            .queryFindNextInvoiceSequential(ruc, documentType, establishment, emissionPoint)
            .orElseGet(() ->  createInitial(ruc, documentType, establishment, emissionPoint));

        // Increment sequential
        invoiceSequential.setLastSequential(invoiceSequential.getLastSequential()+1);    

        // Save sequential
        invoiceSequentialRepository.save(invoiceSequential);
        return String.format("%09d", invoiceSequential.getLastSequential());
    }

    private InvoiceSequential createInitial(String ruc, String documentType, String establishment, String emissionPoint) {
        InvoiceSequential seq = new InvoiceSequential();
        seq.setRuc(ruc);
        seq.setDocumentType(documentType);
        seq.setEstablishmentCode(establishment);
        seq.setEmissionPointCode(emissionPoint);
        seq.setLastSequential(0);
        return seq;
    }
    
}
