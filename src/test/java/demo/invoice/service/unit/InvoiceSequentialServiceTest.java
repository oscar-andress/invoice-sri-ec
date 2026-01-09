package demo.invoice.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import demo.invoice.entity.InvoiceSequential;
import demo.invoice.repository.InvoiceSequentialRepository;
import demo.invoice.service.impl.InvoiceSequentialServiceImpl;

@ExtendWith(MockitoExtension.class)
public class InvoiceSequentialServiceTest {
    @Mock
    private InvoiceSequentialRepository invoiceSequentialRepository;
    
    @InjectMocks
    private InvoiceSequentialServiceImpl invoiceSequentialServiceImpl;

    @Test
    void nextInvoiceSequential_Success_WhenExists(){
        // GIVEN 
        InvoiceSequential invoiceSequential = new InvoiceSequential();
        invoiceSequential.setDocumentType("05");
        invoiceSequential.setEmissionPointCode("01");
        invoiceSequential.setEstablishmentCode("01");
        invoiceSequential.setIdInvoiceSequencial(1l);
        invoiceSequential.setLastSequential(1);
        invoiceSequential.setRuc("1001122334");

        String ruc = "1001122334";
        String establishment = "01";
        String emissionPoint = "01";
        String documentType = "05";

        when(invoiceSequentialRepository.queryFindNextInvoiceSequential(eq(ruc),  eq(documentType), eq(establishment), eq(emissionPoint)))
            .thenReturn(Optional.of(invoiceSequential));
        when(invoiceSequentialRepository.save(invoiceSequential))
            .thenReturn(invoiceSequential);

        // ACT
        String nextInvoiceSequential = invoiceSequentialServiceImpl.nextInvoiceSequential(ruc, establishment, emissionPoint, documentType);

        // THEN
        assertEquals(nextInvoiceSequential, "000000002");
        verify(invoiceSequentialRepository, times(1)).queryFindNextInvoiceSequential(ruc, documentType, establishment, emissionPoint);
        verify(invoiceSequentialRepository, times(1))
            .save(argThat(seq -> seq.getLastSequential().equals(2)));
    }

    @Test
    void nextInvoiceSequential_Success_WhenNotExists(){
        // GIVEN 

        String ruc = "1001122334";
        String establishment = "01";
        String emissionPoint = "01";
        String documentType = "05";

        when(invoiceSequentialRepository.queryFindNextInvoiceSequential(eq(ruc),  eq(documentType), eq(establishment), eq(emissionPoint)))
            .thenReturn(Optional.empty());
        when(invoiceSequentialRepository.save(any(InvoiceSequential.class)))
            .thenAnswer(arg -> arg.getArgument(0));

        // ACT
        String nextInvoiceSequential = invoiceSequentialServiceImpl.nextInvoiceSequential(ruc, establishment, emissionPoint, documentType);

        // THEN
        assertEquals(nextInvoiceSequential, "000000001");
        verify(invoiceSequentialRepository, times(1)).queryFindNextInvoiceSequential(ruc, documentType, establishment, emissionPoint);
        verify(invoiceSequentialRepository, times(1))
            .save(argThat(seq -> seq.getLastSequential().equals(1)));
    }

}
