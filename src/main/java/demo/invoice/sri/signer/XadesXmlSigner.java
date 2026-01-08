package demo.invoice.sri.signer;

import org.springframework.stereotype.Component;

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
            e.printStackTrace();
        }
        return "";
    }
    
}
