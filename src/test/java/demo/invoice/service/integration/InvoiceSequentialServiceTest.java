package demo.invoice.service.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import demo.invoice.entity.InvoiceSequential;
import demo.invoice.repository.InvoiceSequentialRepository;
import demo.invoice.service.InvoiceSequentialService;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional

class InvoiceSequentialServiceTest {

    private final InvoiceSequentialService invoiceSequentialService;
    private final InvoiceSequentialRepository invoiceSequentialRepository;
    private InvoiceSequential invoiceSequential;
    private String documentType = "05";
    private String emissionPointCode = "01";
    private String establishmentCode = "01";
    private int lastSequential = 1;
    private String ruc = "1001122334";

    @Autowired
    InvoiceSequentialServiceTest(InvoiceSequentialService invoiceSequentialService,
                                 InvoiceSequentialRepository invoiceSequentialRepository
    ){
        this.invoiceSequentialService = invoiceSequentialService;
        this.invoiceSequentialRepository = invoiceSequentialRepository;
    }

    @BeforeEach
    void setUp(){
        invoiceSequentialRepository.deleteAll();
    }

    @Test
    void nextInvoiceSequential_Success_WhenExist(){

        // WHEN
        invoiceSequential = new InvoiceSequential();
        invoiceSequential.setDocumentType(documentType);
        invoiceSequential.setEmissionPointCode(emissionPointCode);
        invoiceSequential.setEstablishmentCode(establishmentCode);
        invoiceSequential.setLastSequential(lastSequential);
        invoiceSequential.setRuc(ruc);

        invoiceSequentialRepository.save(invoiceSequential);

        String nextInvoiceSequentialExpected = "000000002";
        
        // ACT
        String nextInvoiceSequential = 
            invoiceSequentialService.nextInvoiceSequential(
                ruc, establishmentCode, emissionPointCode, documentType);
        
        // THEN
        assertNotNull(nextInvoiceSequential);
        assertEquals(nextInvoiceSequentialExpected, nextInvoiceSequential);

        // VERIFY
        InvoiceSequential updatedInvoiceSequential = invoiceSequentialRepository.findById(invoiceSequential.getIdInvoiceSequencial()).orElseThrow();
        assertEquals(2, updatedInvoiceSequential.getLastSequential());
    }

    @Test
    void nextInvoiceSequential_Success_WhenNotExist(){

        // WHEN
        String nextInvoiceSequentialExpected = "000000001";
        
        // ACT
        String nextInvoiceSequential = 
            invoiceSequentialService.nextInvoiceSequential(
                ruc, establishmentCode, emissionPointCode, documentType);
        
        // THEN
        assertNotNull(nextInvoiceSequential);
        assertEquals(nextInvoiceSequentialExpected, nextInvoiceSequential);

        // VERIFY
        InvoiceSequential savedInvoiceSequential 
            = invoiceSequentialRepository
              .queryFindNextInvoiceSequential(ruc, documentType, establishmentCode, emissionPointCode)
              .orElseThrow();
        
        assertEquals(1, savedInvoiceSequential.getLastSequential());
    }
}
