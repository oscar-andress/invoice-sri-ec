package demo.invoice.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import demo.invoice.domain.calculator.InvoiceCalculator;
import demo.invoice.domain.calculator.InvoiceTotals;
import demo.invoice.domain.context.InvoiceContext;
import demo.invoice.domain.factory.InvoiceFactory;
import demo.invoice.dto.request.IssueInvoiceRequest;
import demo.invoice.dto.request.SendInvoiceRequest;
import demo.invoice.dto.response.IssueInvoiceResponse;
import demo.invoice.dto.response.SendInvoiceResponse;
import demo.invoice.entity.Invoice;
import demo.invoice.entity.Issuer;
import demo.invoice.entity.IssuerConfig;
import demo.invoice.enumeration.InvoiceStatus;
import demo.invoice.mapper.InvoiceMapper;
import demo.invoice.mapper.SriInvoiceMapper;
import demo.invoice.repository.InvoiceRepository;
import demo.invoice.repository.IssuerConfigRepository;
import demo.invoice.repository.IssuerRepository;
import demo.invoice.service.InvoiceSequentialService;
import demo.invoice.service.InvoiceService;
import demo.invoice.sri.accesskey.AccessKeyGenerator;
import demo.invoice.sri.reception.SriInvoiceSender;
import demo.invoice.sri.signer.XmlSigner;
import demo.invoice.sri.xml.SriInvoiceXml;
import demo.invoice.sri.xml.SriXmlGenerator;
import jakarta.transaction.Transactional;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    private final SriXmlGenerator sriXmlGenerator;
    private final SriInvoiceMapper sriInvoiceMapper;
    private final Map<String, XmlSigner> xmlSigners;
    private final IssuerRepository issuerRepository;
    private final IssuerConfigRepository issuerConfigRepository;
    private final InvoiceSequentialService invoiceSequentialService;
    private final InvoiceCalculator invoiceCalculator;
    private final InvoiceFactory invoiceFactory;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final AccessKeyGenerator accessKeyGenerator;
    private final SriInvoiceSender sriInvoiceSender;

    InvoiceServiceImpl(Map<String, XmlSigner> xmlSigners, 
                       SriXmlGenerator sriXmlGenerator,
                       SriInvoiceMapper sriInvoiceMapper,
                       IssuerRepository issuerRepository,
                       IssuerConfigRepository issuerConfigRepository,
                       InvoiceSequentialService invoiceSequentialService, 
                       InvoiceCalculator invoiceCalculator,
                       InvoiceFactory invoiceFactory,
                       InvoiceRepository invoiceRepository,
                       InvoiceMapper invoiceMapper,
                       AccessKeyGenerator accessKeyGenerator,
                       SriInvoiceSender sriInvoiceSender){
        this.xmlSigners = xmlSigners;
        this.sriXmlGenerator = sriXmlGenerator;
        this.sriInvoiceMapper = sriInvoiceMapper;
        this.issuerRepository = issuerRepository;
        this.issuerConfigRepository = issuerConfigRepository;
        this.invoiceSequentialService = invoiceSequentialService;
        this.invoiceCalculator = invoiceCalculator;
        this.invoiceFactory = invoiceFactory;
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.accessKeyGenerator = accessKeyGenerator;
        this.sriInvoiceSender = sriInvoiceSender;
    }

    @Transactional
    @Override
    public IssueInvoiceResponse issueInvoice(IssueInvoiceRequest request) {
        
        // Find issuer data
        Issuer issuer = issuerRepository.findByActiveTrue();
        
        // Find issuer config 
        IssuerConfig issuerConfig = issuerConfigRepository.findByIdIssuer(issuer.getIdIssuer());
        
        // Generate next sequential
        String nextInvoiceSequential = invoiceSequentialService
                                            .nextInvoiceSequential( 
                                                issuer.getRuc(), 
                                                issuerConfig.getEstablishmentCode(), 
                                                issuerConfig.getEmissionPointCode(),
                                                "01");
        
        // Generate accessKey
        String accessKey = accessKeyGenerator.generate(
                                                LocalDate.now(),                                                                   // Fecha de emisión
                                                "01",                                                        // Tipo comprobante (factura)
                                                issuer.getRuc(),                                                              // RUC del emisor
                                                issuerConfig.getEnvironment(),                                                // Ambiente: 1=pruebas, 2=producción
                                                issuerConfig.getEstablishmentCode(), 
                                                issuerConfig.getEmissionPointCode(),    // Serie: establecimiento+puntoEmision
                                                nextInvoiceSequential,                                                       // Secuencial
                                                issuerConfig.getEmissionType()    
                                            );
        
        // Calculate invoice totals
        InvoiceTotals invoiceTotals = invoiceCalculator.calculate(request.getDetails());

        // Context data for mapping to SRI xml
        InvoiceContext invoiceContext = new InvoiceContext(issuer, issuerConfig, nextInvoiceSequential, accessKey);

        // Map request to SRI XML
        SriInvoiceXml sriInvoiceXml = sriInvoiceMapper.mapToSriInvoiceMapper(request, invoiceContext, invoiceTotals);
        
        // Generate invoice xml
        String unsignedXml = sriXmlGenerator.generate(sriInvoiceXml);

        // Sign xml
        XmlSigner xmlSigner = xmlSigners.get("XADES"); 
        String signedXml = xmlSigner.sign(unsignedXml);

        // Create invoice
        Invoice invoice = invoiceFactory.create(request, invoiceContext, unsignedXml, signedXml, invoiceTotals, accessKey, nextInvoiceSequential);

        // Save invoice
        Invoice savedInvoice = invoiceRepository.save(invoice);

        return invoiceMapper.toIssueResponse(savedInvoice);
    }

    @Override
    public List<SendInvoiceResponse> sendInvoices(List<SendInvoiceRequest> requests) {

        List<Long> idInvoices = requests
            .stream()
            .map(SendInvoiceRequest :: getIdInvoice)
            .toList();

        // Find invoices
        List<Invoice> invoices = invoiceRepository
            .queryFindByIdInvoiceInAndStatus(idInvoices, InvoiceStatus.ISSUED.toString());
        
        if(invoices.isEmpty()){
            return List.of();
        }
        
        // Send to SRI
        List<SendInvoiceResponse> responses = invoices
            .stream()
            .map(invoice -> {
                String status = sriInvoiceSender.send(invoice.getSignedXml());
                invoice.setStatus(status);
                return invoiceMapper.toSendInvoiceResponse(invoice);
            })
            .toList();
        
        // Save status changes
        invoiceRepository.saveAll(invoices);

        return responses;
    }

}
