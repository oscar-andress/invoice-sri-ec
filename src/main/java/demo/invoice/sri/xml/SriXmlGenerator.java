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
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
            
            StringWriter writer = new StringWriter();
            marshaller.marshal(invoice, writer);

            System.out.println("XML generado:");
            System.out.println(writer.toString().substring(0, Math.min(500, writer.toString().length())));
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
