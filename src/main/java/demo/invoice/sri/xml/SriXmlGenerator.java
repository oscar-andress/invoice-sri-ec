package demo.invoice.sri.xml;

import java.io.StringWriter;

import org.springframework.stereotype.Component;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

@Component
public class SriXmlGenerator {

    public String generate(SriInvoiceXml invoice) {
        try {
            JAXBContext context = JAXBContext.newInstance(SriInvoiceXml.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            StringWriter writer = new StringWriter();
            marshaller.marshal(invoice, writer);
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
