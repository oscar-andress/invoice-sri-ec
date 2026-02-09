package demo.invoice.sri.signing.strategy;

import org.w3c.dom.Document;

import xades4j.properties.DataObjectDesc;

public interface DataObjectDescStrategy {
    DataObjectDesc createDataObjectDesc(Document doc);
}
