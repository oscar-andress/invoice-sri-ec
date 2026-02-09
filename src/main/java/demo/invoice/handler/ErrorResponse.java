package demo.invoice.handler;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String timestamp;
    private int status;
    private String message;
    private String errorCode;
    private String path;

    public ErrorResponse(){
        this.timestamp = LocalDateTime.now().toString();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String erroCode) {
        this.errorCode = erroCode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
