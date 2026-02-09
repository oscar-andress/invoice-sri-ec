package demo.invoice.sri.signing.infrastructure.xml;

import org.w3c.dom.Document;

public interface XmlSerializer {
    String serialize(Document document);
}
