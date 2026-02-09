package demo.invoice.sri.signing.signer;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import demo.invoice.handler.XadesSignerException;
import demo.invoice.sri.signing.CertificateData;
import demo.invoice.sri.signing.infrastructure.certificate.CertificateLoader;
import demo.invoice.sri.signing.infrastructure.xades.XadesProfileFactory;
import demo.invoice.sri.signing.infrastructure.xml.XmlParser;
import demo.invoice.sri.signing.infrastructure.xml.XmlSerializer;
import demo.invoice.sri.signing.strategy.DataObjectDescStrategy;
import xades4j.XAdES4jException;
import xades4j.production.SignedDataObjects;
import xades4j.properties.DataObjectDesc;

@Component
public class XadesSigner implements XmlSigner{
    private final CertificateLoader certificateLoader;
    private final XmlParser xmlParser;
    private final XmlSerializer xmlSerializer;
    private final XadesProfileFactory xadesProfileFactory;
    private final DataObjectDescStrategy dataObjectDescStrategy;

    public XadesSigner(CertificateLoader certificateLoader,
                    XmlParser xmlParser,
                    XmlSerializer xmlSerializer,
                    XadesProfileFactory xadesProfileFactory,
                    DataObjectDescStrategy dataObjectDescStrategy) 
    {
        this.certificateLoader = certificateLoader;
        this.xmlParser = xmlParser;
        this.xmlSerializer = xmlSerializer;
        this.xadesProfileFactory = xadesProfileFactory;
        this.dataObjectDescStrategy = dataObjectDescStrategy;
    }
    
    @Override
    public String sign(String xml, String path, String password) {
        
        try {

            // Load certificate PKC12
            CertificateData certificateData = certificateLoader.load(path, password);

            // Parse xml to document
            Document document = xmlParser.parse(xml);
            
            // Create descriptor of the object to sign
            DataObjectDesc dataObjectDesc =dataObjectDescStrategy.createDataObjectDesc(document);
            
            // DOM ID is the id attribute
            document.getDocumentElement().setIdAttribute("id", true);

            // Create signer
            xades4j.production.XadesSigner xadesSigner = xadesProfileFactory.createSigner(certificateData);
        
            // Sign documento
            xadesSigner.sign(new SignedDataObjects(dataObjectDesc), document.getDocumentElement());
            
            // Serialize document
            return xmlSerializer.serialize(document);
            
        } catch (XAdES4jException e) {
            throw new XadesSignerException(e.getMessage(), e.getCause());
        }

    }
}
