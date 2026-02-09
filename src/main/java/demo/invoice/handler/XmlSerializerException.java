package demo.invoice.handler;

public class XmlSerializerException extends SignatureException{
    
    public XmlSerializerException(String message, Throwable cause) {
        super(message, "XML_SERIALIZING_ERROR", cause);
    }    
}
