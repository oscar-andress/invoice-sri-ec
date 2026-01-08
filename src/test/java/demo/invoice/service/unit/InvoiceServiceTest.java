package demo.invoice.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import demo.invoice.domain.calculator.InvoiceCalculator;
import demo.invoice.domain.calculator.InvoiceTotals;
import demo.invoice.domain.context.InvoiceContext;
import demo.invoice.domain.factory.InvoiceFactory;
import demo.invoice.dto.request.IssueInvoiceRequest;
import demo.invoice.dto.request.IssueInvoiceRequest.IssueInvoiceDetailRequest;
import demo.invoice.dto.response.IssueInvoiceResponse;
import demo.invoice.entity.Invoice;
import demo.invoice.entity.Issuer;
import demo.invoice.entity.IssuerConfig;
import demo.invoice.mapper.InvoiceMapper;
import demo.invoice.mapper.SriInvoiceMapper;
import demo.invoice.repository.InvoiceRepository;
import demo.invoice.repository.IssuerConfigRepository;
import demo.invoice.repository.IssuerRepository;
import demo.invoice.service.InvoiceSequentialService;
import demo.invoice.service.impl.InvoiceServiceImpl;
import demo.invoice.sri.signer.XmlSigner;
import demo.invoice.sri.xml.SriInvoiceXml;
import demo.invoice.sri.xml.SriXmlGenerator;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceTest {
    
    @Mock
    private  SriXmlGenerator sriXmlGenerator;

    @Mock
    private  SriInvoiceMapper sriInvoiceMapper;

    @Mock
    private  Map<String, XmlSigner> xmlSigners;

    @Mock
    private  IssuerRepository issuerRepository;
    
    @Mock
    private  IssuerConfigRepository issuerConfigRepository;
    
    @Mock
    private  InvoiceSequentialService invoiceSequentialService;
    
    @Mock
    private  InvoiceCalculator invoiceCalculator;
    
    @Mock
    private  InvoiceFactory invoiceFactory;
    
    @Mock
    private  InvoiceRepository invoiceRepository;
    
    @Mock
    private  InvoiceMapper invoiceMapper;

    @Mock
    private XmlSigner xmlSigner;

    @InjectMocks
    private InvoiceServiceImpl invoiceServiceImpl;

    private IssueInvoiceRequest issueInvoiceRequest;
    private IssueInvoiceRequest.IssueInvoiceDetailRequest issueInvoiceDetailRequest;
    private List<IssueInvoiceRequest.IssueInvoiceDetailRequest> details;
    private Issuer issuer = new Issuer();
    private IssuerConfig issuerConfig = new IssuerConfig();
    private InvoiceTotals invoiceTotals;

    @BeforeEach
    void setUp(){
        // GIVEN ISSUER
        issuer = new Issuer();
        issuer.setActive(true);
        issuer.setIdIssuer(1);
        issuer.setLegalName("DEVOS");
        issuer.setRuc("1001122334");
        issuer.setHeadOfficeAddress("FAR FAR WAY");
        issuer.setCreatedAt(LocalDateTime.now());
        issuer.setAccountingRequired(true);

        // GIVEN
        issuerConfig = new IssuerConfig();
        issuerConfig.setCreatedAt(LocalDateTime.now());
        issuerConfig.setEmissionPointCode("001");
        issuerConfig.setEmissionType("01");
        issuerConfig.setEnvironment("1");
        issuerConfig.setEstablishmentCode("001");
        issuerConfig.setIdIssuer(issuer.getIdIssuer());
        issuerConfig.setIdIssuerConfig(1);

        // GIVEN REQUEST
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
        issueInvoiceDetailRequest.setTaxPercentage(new BigDecimal(12));
        issueInvoiceDetailRequest.setTaxPercentageCode("2");
        issueInvoiceDetailRequest.setUnitPrice(new BigDecimal(10));

        details = new ArrayList<>();
        details.add(issueInvoiceDetailRequest);
        issueInvoiceRequest.setDetails(details);

        // GIVEN invoice totals
        invoiceTotals = new InvoiceTotals();
        invoiceTotals.setGrandTotal(new BigDecimal(11.20));
        invoiceTotals.setTotalDiscount(BigDecimal.ZERO);
        invoiceTotals.setTotalTaxes(new BigDecimal(1.20));
        invoiceTotals.setTotalWithoutTaxes(new BigDecimal(10));
    }

    @Test
    void issueInvoice_Success(){
        SriInvoiceXml sriInvoiceXml = new SriInvoiceXml();
        String unsignedXml = "<xml>unsigned</xml>";
        String signedXml = "<xml>signed</xml>";
        Invoice invoice = new Invoice();
        invoice.setEnvironment("1");
        Invoice savedInvoice = new Invoice();
        savedInvoice.setIdInvoice(1l);
        IssueInvoiceResponse response = new IssueInvoiceResponse();
        response.setIdInvoice(1l);

        // WHEN
        when(issuerRepository.findByActiveTrue()).thenReturn(issuer);
        when(issuerConfigRepository.findByIdIssuer(issuer.getIdIssuer())).thenReturn(issuerConfig);
        when(invoiceSequentialService
                .nextInvoiceSequential( 
                    issuer.getRuc(), 
                    issuerConfig.getEstablishmentCode(), 
                    issuerConfig.getEmissionPointCode(),
                    "01")
        ).thenReturn("000000001");
        when(invoiceCalculator.calculate(details)).thenReturn(invoiceTotals);
        when(sriInvoiceMapper.mapToSriInvoiceMapper(eq(issueInvoiceRequest), any(InvoiceContext.class), eq(invoiceTotals)))
            .thenReturn(sriInvoiceXml);
        when(sriXmlGenerator.generate(sriInvoiceXml)).thenReturn(unsignedXml);
        when(xmlSigners.get("XADES")).thenReturn(xmlSigner);
        when(xmlSigner.sign(unsignedXml)).thenReturn(signedXml);
        when(invoiceFactory.create(
                eq(issueInvoiceRequest),
                any(InvoiceContext.class),
                eq(unsignedXml),
                eq(signedXml),
                eq(invoiceTotals),
                anyString(),              
                eq("000000001")
        )).thenReturn(invoice);
        when(invoiceRepository.save(invoice)).thenReturn(savedInvoice);
        when(invoiceMapper.toIssueResponse(savedInvoice)).thenReturn(response);

        // ACT
        IssueInvoiceResponse responseReturned = invoiceServiceImpl.issueInvoice(issueInvoiceRequest);

        // THEN
        assertNotNull(responseReturned);
        assertEquals(response, responseReturned);
    }
}
