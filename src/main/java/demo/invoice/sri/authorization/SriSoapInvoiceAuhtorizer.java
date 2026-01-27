package demo.invoice.sri.authorization;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.springframework.stereotype.Component;

import ec.sri.ws.autorizacion.AutorizacionComprobantesOffline;
import ec.sri.ws.autorizacion.AutorizacionComprobantesOfflineService;
import ec.sri.ws.autorizacion.RespuestaComprobante;

@Component
public class SriSoapInvoiceAuhtorizer implements SriInvoiceAuthorizer{

    @Override
    public RespuestaComprobante authorize(String accessKey) {

        AutorizacionComprobantesOfflineService autorizacion = new AutorizacionComprobantesOfflineService();
        AutorizacionComprobantesOffline port = autorizacion.getAutorizacionComprobantesOfflinePort();

            Client client = ClientProxy.getClient(port);

            client.getInInterceptors().add(new LoggingInInterceptor());
            client.getOutInterceptors().add(new LoggingOutInterceptor());
        return port.autorizacionComprobante(accessKey);
    }
    
}
