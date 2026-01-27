package demo.invoice.sri.authorization;

import ec.sri.ws.autorizacion.RespuestaComprobante;

public interface SriInvoiceAuthorizer {
    RespuestaComprobante authorize(String accessKey);
}
