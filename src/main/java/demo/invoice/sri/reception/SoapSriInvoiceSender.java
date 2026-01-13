package demo.invoice.sri.reception;

import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

import ec.sri.ws.recepcion.RecepcionComprobantesOffline;
import ec.sri.ws.recepcion.RecepcionComprobantesOfflineService;
import ec.sri.ws.recepcion.RespuestaSolicitud;

@Component
public class SoapSriInvoiceSender implements SriInvoiceSender{

    @Override
    public String send(String signedXml) {
        RecepcionComprobantesOfflineService reception = new RecepcionComprobantesOfflineService();
        RecepcionComprobantesOffline port = reception.getRecepcionComprobantesOfflinePort();

        byte[] xmlBytes = signedXml.getBytes(StandardCharsets.UTF_8);
        RespuestaSolicitud resposeRequest = port.validarComprobante(xmlBytes);
        return resposeRequest.getEstado();
    }
    
}
