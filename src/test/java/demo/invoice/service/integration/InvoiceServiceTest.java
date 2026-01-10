package demo.invoice.service.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import demo.invoice.dto.request.IssueInvoiceRequest;
import demo.invoice.dto.request.IssueInvoiceRequest.IssueInvoiceDetailRequest;
import demo.invoice.dto.response.IssueInvoiceResponse;
import demo.invoice.entity.Invoice;
import demo.invoice.entity.Issuer;
import demo.invoice.entity.IssuerConfig;
import demo.invoice.repository.InvoiceRepository;
import demo.invoice.repository.IssuerConfigRepository;
import demo.invoice.repository.IssuerRepository;
import demo.invoice.service.InvoiceService;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional

class InvoiceServiceTest {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceService invoiceService;
    private final IssuerRepository issuerRepository;
    private final IssuerConfigRepository issuerConfigRepository;
    private IssueInvoiceRequest issueInvoiceRequest;
    private IssueInvoiceRequest.IssueInvoiceDetailRequest issueInvoiceDetailRequest;
    private IssuerConfig issuerConfig;
    private Issuer issuer;
    
    @Autowired
    InvoiceServiceTest(InvoiceRepository invoiceRepository,
                       InvoiceService invoiceService,
                       IssuerRepository issuerRepository,
                       IssuerConfigRepository issuerConfigRepository){
        this.invoiceRepository = invoiceRepository;
        this.invoiceService = invoiceService;
        this.issuerRepository = issuerRepository;
        this.issuerConfigRepository = issuerConfigRepository;
    }

    @BeforeEach
    void setUp(){
        // Clean
        issuerRepository.deleteAll();
        issuerConfigRepository.deleteAll();
        invoiceRepository.deleteAll();

        // GIVEN
        // Request data
        issueInvoiceRequest = new IssueInvoiceRequest();
        issueInvoiceRequest.setBuyerAddress("Av. Amazonas y Naciones Unidas");
        issueInvoiceRequest.setBuyerIdentification("0101010101");
        issueInvoiceRequest.setBuyerIdentificationType("05");
        issueInvoiceRequest.setBuyerName("Juan Perez");

        issueInvoiceDetailRequest = new IssueInvoiceDetailRequest();
        issueInvoiceDetailRequest.setDescription("Software development service");
        issueInvoiceDetailRequest.setDiscount(BigDecimal.ZERO);
        issueInvoiceDetailRequest.setQuantity(BigDecimal.ONE);
        issueInvoiceDetailRequest.setTaxCode("2");
        issueInvoiceDetailRequest.setTaxPercentage(new BigDecimal(12.00));
        issueInvoiceDetailRequest.setTaxPercentageCode("2");
        issueInvoiceDetailRequest.setUnitPrice(new BigDecimal(100.00));
        
        List<IssueInvoiceRequest.IssueInvoiceDetailRequest> details = new ArrayList<>();
        details.add(issueInvoiceDetailRequest);
        issueInvoiceRequest.setDetails(details);

        // Issuer
        issuer = new Issuer();
        issuer.setActive(true);
        issuer.setAccountingRequired(true);
        issuer.setHeadOfficeAddress("FAR FAR AWAY");
        issuer.setLegalName("DEVOS");
        issuer.setRuc("1001122334");
        issuerRepository.save(issuer);

        // Issuer config
        issuerConfig = new IssuerConfig();
        issuerConfig.setEmissionPointCode("01");
        issuerConfig.setEmissionType("1");
        issuerConfig.setEnvironment("1");
        issuerConfig.setEstablishmentCode("01");
        issuerConfig.setIdIssuer(issuer.getIdIssuer());
        issuerConfigRepository.save(issuerConfig);
    }

    @Test
    void issueInvoice_Success(){
        // ACT
        IssueInvoiceResponse issueInvoiceResponse = invoiceService.issueInvoice(issueInvoiceRequest);

        // THEN
        assertNotNull(issueInvoiceResponse);
        assertNotNull(issueInvoiceResponse.getAccessKey());
        assertNotNull(issueInvoiceResponse.getIdInvoice());
        assertNotNull(issueInvoiceResponse.getStatus());

        // Verify
        Invoice savedInvoice = invoiceRepository.findById(issueInvoiceResponse.getIdInvoice()).orElseThrow();
        assertEquals(issueInvoiceResponse.getIdInvoice(), savedInvoice.getIdInvoice());
    }
}
