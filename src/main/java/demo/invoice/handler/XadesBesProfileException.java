package demo.invoice.handler;

public class XadesBesProfileException extends SignatureException{

    public XadesBesProfileException(String message, Throwable cause) {
        super(message, "XADES_BES_PROFILE_ERROR", cause);
    }
}
