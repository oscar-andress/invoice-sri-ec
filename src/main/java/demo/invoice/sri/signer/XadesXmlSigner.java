package demo.invoice.sri.signer;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component("XADES")
public class XadesXmlSigner implements XmlSigner{

    private final XadesSigner xadesSigner;

    XadesXmlSigner(XadesSigner xadesSigner){
        this.xadesSigner =xadesSigner;
    }

    @Override
    public String sign(String unsignedXml) {
        
        try {
            return xadesSigner.signXml(unsignedXml, "src/main/resources/static/signature/yourSign.p12", "yourSuperSecretKey");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
}
