package demo.invoice.handler;

public class XadesSignerException extends SignatureException{
    public XadesSignerException(String message, Throwable cause) {
        super(message, "XADES_SIGNER_ERROR", cause);
    }
}
