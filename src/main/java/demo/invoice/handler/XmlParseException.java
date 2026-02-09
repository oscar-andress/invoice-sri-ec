package demo.invoice.handler;

public class XmlParseException extends SignatureException{
    
    public XmlParseException(String message, Throwable cause) {
        super(message, "XML_PARSING_ERROR", cause);
    }
}
