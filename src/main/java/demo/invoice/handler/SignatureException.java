package demo.invoice.handler;

public abstract class SignatureException extends RuntimeException{
    
    private final String errorCode;
    
    protected SignatureException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    protected SignatureException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode(){
        return errorCode;
    }

}
