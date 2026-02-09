package demo.invoice.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final String MESSAGE = "message";

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE, ex.getReason());
        return new ResponseEntity<>(error, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE, "An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Map<String, String>> handleAllErrors(Throwable ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE, "A critical error occurred: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CertificateLoadException.class)
    public ResponseEntity<ErrorResponse> handleCertificateLoadException(CertificateLoadException ex, WebRequest request){
        return buildErrorResponse(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCertificateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCertificateHandler(InvalidCertificateException ex, WebRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.CONFLICT);
    }

    private String getPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(SignatureException ex,
                                                             WebRequest request,
                                                             HttpStatus status
    ) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ex.getErrorCode());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(getPath(request));
        errorResponse.setStatus(status.value());

        return new ResponseEntity<>(errorResponse, status);
    }
}
