package demo.invoice.handler;

public class CertificateLoadException extends SignatureException{

    public CertificateLoadException(String message) {
        super(message, "CERT_LOAD_ERROR");
    }
    
    public CertificateLoadException(String message, Throwable cause) {
        super(message, "CERT_LOAD_ERROR", cause);
    }
}
