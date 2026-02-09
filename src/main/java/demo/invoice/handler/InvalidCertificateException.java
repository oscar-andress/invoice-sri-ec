package demo.invoice.handler;

public class InvalidCertificateException extends SignatureException{

    public InvalidCertificateException(String message, Throwable cause) {
        super(message, "INVALID_CERTIFICATE", cause);
    }

    public InvalidCertificateException(String message) {
        super(message, "INVALID_CERTIFICATE");
    }
}
