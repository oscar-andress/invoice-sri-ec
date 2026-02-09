package demo.invoice.sri.signing.strategy;

import org.w3c.dom.Document;

import xades4j.algorithms.EnvelopedSignatureTransform;
import xades4j.production.DataObjectReference;
import xades4j.properties.DataObjectDesc;

public class SriEnvelopedSignatureStrategy implements DataObjectDescStrategy{

    @Override
    public DataObjectDesc createDataObjectDesc(Document doc) {
        return new DataObjectReference("#" + doc.getDocumentElement().getAttribute("id"))
            .withTransform(new EnvelopedSignatureTransform());
    }
    
}
