package demo.invoice.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import demo.invoice.context.InvoiceContext;
import demo.invoice.dto.request.InvoiceRequest;
import demo.invoice.entity.Issuer;
import demo.invoice.entity.IssuerConfig;
import demo.invoice.mapper.SriInvoiceMapper;
import demo.invoice.repository.IssuerConfigRepository;
import demo.invoice.repository.IssuerRepository;
import demo.invoice.service.InvoiceSequentialService;
import demo.invoice.service.InvoiceService;
import demo.invoice.sri.signer.XmlSigner;
import demo.invoice.sri.xml.SriInvoiceXml;
import demo.invoice.sri.xml.SriXmlGenerator;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    private final SriXmlGenerator sriXmlGenerator;
    private final SriInvoiceMapper sriInvoiceMapper;
    private final Map<String, XmlSigner> xmlSigners;
    private final IssuerRepository issuerRepository;
    private final IssuerConfigRepository issuerConfigRepository;
    private final InvoiceSequentialService invoiceSequentialService;

    InvoiceServiceImpl(Map<String, XmlSigner> xmlSigners, 
                       SriXmlGenerator sriXmlGenerator,
                       SriInvoiceMapper sriInvoiceMapper,
                       IssuerRepository issuerRepository,
                       IssuerConfigRepository issuerConfigRepository,
                       InvoiceSequentialService invoiceSequentialService){
        this.xmlSigners = xmlSigners;
        this.sriXmlGenerator = sriXmlGenerator;
        this.sriInvoiceMapper = sriInvoiceMapper;
        this.issuerRepository = issuerRepository;
        this.issuerConfigRepository = issuerConfigRepository;
        this.invoiceSequentialService = invoiceSequentialService;
    }

    @Override
    public String issueInvoice(InvoiceRequest request) {
        
        // Find issuer data
        Issuer issuer = issuerRepository.findByActiveTrue();
        IssuerConfig issuerConfig = issuerConfigRepository.findByIdIssuer(issuer.getIdIssuer());
        String nextInvoiceSequential = invoiceSequentialService
                                            .nextInvoiceSequential( 
                                                issuer.getRuc(), 
                                                issuerConfig.getEstablishmentCode(), 
                                                issuerConfig.getEmissionPointCode(),
                                  "01");
        
        // Context data for mapping to SRI xml
        InvoiceContext invoiceContext = new InvoiceContext(issuer, issuerConfig, nextInvoiceSequential);

        // Map request to SRI XML
        SriInvoiceXml sriInvoiceXml = sriInvoiceMapper.mapToSriInvoiceMapper(request, invoiceContext);
        
        // Generate invoice xml
        String xmlPath = "src/main/resources/static/invoice.xml";
        String signXmlPath = "src/main/resources/static/signInvoice.xml"; 
        sriXmlGenerator.generate(sriInvoiceXml, xmlPath);
        XmlSigner xmlSigner = xmlSigners.get("XADES"); 
        return xmlSigner.sign(xmlPath, signXmlPath);
    }
}
