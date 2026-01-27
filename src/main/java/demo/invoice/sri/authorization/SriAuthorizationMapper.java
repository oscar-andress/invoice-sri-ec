package demo.invoice.sri.authorization;

import java.time.LocalDate;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Component;

import demo.invoice.sri.authorization.dto.SriAuthorizationMessage;
import demo.invoice.sri.authorization.dto.SriAuthorization;
import demo.invoice.sri.authorization.dto.SriResponseReceipt;
import demo.invoice.sri.authorization.dto.SriAuthorization.SriAuthorizationMessages;
import demo.invoice.sri.authorization.dto.SriResponseReceipt.Authorizations;
import ec.sri.ws.autorizacion.Autorizacion;
import ec.sri.ws.autorizacion.Mensaje;
import ec.sri.ws.autorizacion.RespuestaComprobante;

@Component
public class SriAuthorizationMapper {

    public SriResponseReceipt toSriResponseReceipt(RespuestaComprobante response){
        SriResponseReceipt sriResponse = new SriResponseReceipt();
        sriResponse.setAccessKeyConsulted(response.getClaveAccesoConsultada());
        sriResponse.setNumberReceipts(response.getNumeroComprobantes());

        SriResponseReceipt.Authorizations authorizations = new Authorizations(); 
        authorizations.setAuthorization(
            response.getAutorizaciones().getAutorizacion()
                .stream()
                .map(SriAuthorizationMapper :: toSriAuthorization)
                .toList()
        );
        sriResponse.setAuthorizations(authorizations);


        return sriResponse;
    }

    public static SriAuthorization toSriAuthorization(Autorizacion authorization){
        SriAuthorization sriAuthorization = new SriAuthorization();
        
        XMLGregorianCalendar authorizationDateGregorian = authorization.getFechaAutorizacion();
        LocalDate authorizationDate = LocalDate.of(authorizationDateGregorian.getYear(), authorizationDateGregorian.getMonth(), authorizationDateGregorian.getDay());
        
        sriAuthorization.setAuthorizationDate(authorizationDate);
        sriAuthorization.setAuthorizationNumber(authorization.getNumeroAutorizacion());
        sriAuthorization.setEnviroment(authorization.getAmbiente());
        sriAuthorization.setReceipt(authorization.getComprobante());
        sriAuthorization.setStatus(authorization.getEstado());
        
        SriAuthorization.SriAuthorizationMessages messages = new SriAuthorizationMessages();
        messages.setMessage(
            authorization.getMensajes().getMensaje()
                .stream()
                .map(SriAuthorizationMapper :: toSriAuthorizationMessage)
                .toList()
        );

        sriAuthorization.setMessages(messages);

        return sriAuthorization;
    }

    public static SriAuthorizationMessage toSriAuthorizationMessage(Mensaje mensaje){
        SriAuthorizationMessage sriAuthorizarionMessage = new SriAuthorizationMessage();
        sriAuthorizarionMessage.setAdditionalInformtion(mensaje.getInformacionAdicional());
        sriAuthorizarionMessage.setIdentifyer(mensaje.getIdentificador());
        sriAuthorizarionMessage.setMessage(mensaje.getMensaje());
        sriAuthorizarionMessage.setType(mensaje.getTipo());
        return sriAuthorizarionMessage;
    }

}
