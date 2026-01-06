package demo.invoice.sri.xml;

import java.io.File;

import org.springframework.stereotype.Component;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

@Component
public class SriXmlGenerator {

    public String generate(SriInvoiceXml invoice, String outputPath) {
        try {
            JAXBContext context = JAXBContext.newInstance(SriInvoiceXml.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(invoice, new File(outputPath));
            return outputPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputPath;
    }

}
