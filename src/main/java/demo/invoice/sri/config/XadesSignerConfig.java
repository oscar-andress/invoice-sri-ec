package demo.invoice.sri.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.invoice.sri.signing.infrastructure.certificate.CertificateLoader;
import demo.invoice.sri.signing.infrastructure.certificate.Pkcs12CertificateLoader;
import demo.invoice.sri.signing.infrastructure.xades.XadesBesProfileFactory;
import demo.invoice.sri.signing.infrastructure.xades.XadesProfileFactory;
import demo.invoice.sri.signing.infrastructure.xml.DocumentXmlParser;
import demo.invoice.sri.signing.infrastructure.xml.DocumentXmlSerializer;
import demo.invoice.sri.signing.infrastructure.xml.XmlParser;
import demo.invoice.sri.signing.infrastructure.xml.XmlSerializer;
import demo.invoice.sri.signing.signer.XadesSigner;
import demo.invoice.sri.signing.strategy.DataObjectDescStrategy;
import demo.invoice.sri.signing.strategy.SriEnvelopedSignatureStrategy;

@Configuration
public class XadesSignerConfig {

    @Bean
    public CertificateLoader certificateLoader() {
        return new Pkcs12CertificateLoader();
    }

    @Bean
    public XmlParser xmlParser() {
        return new DocumentXmlParser();
    }

    @Bean
    public XmlSerializer xmlSerializer() {
        return new DocumentXmlSerializer();
    }

    @Bean
    public DataObjectDescStrategy dataObjectDescStrategy() {
        return new SriEnvelopedSignatureStrategy();
    }

    @Bean
    public XadesProfileFactory xadesProfileFactory() {
        return new XadesBesProfileFactory();
    }

    @Bean
    public XadesSigner xadesSigner(CertificateLoader certificateLoader,
                                           XmlParser xmlParser,
                                           XmlSerializer xmlSerializer,
                                           DataObjectDescStrategy dataObjectDescStrategy,
                                           XadesProfileFactory xadesProfileFactory
    ){
        return new XadesSigner(certificateLoader, 
                                   xmlParser, 
                                   xmlSerializer, 
                                   xadesProfileFactory, 
                                   dataObjectDescStrategy);
    }
}