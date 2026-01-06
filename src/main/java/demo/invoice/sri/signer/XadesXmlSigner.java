package demo.invoice.sri.signer;

import org.springframework.stereotype.Component;

@Component("XADES")
public class XadesXmlSigner implements XmlSigner{

    private final XadesSigner xadesSigner;

    XadesXmlSigner(XadesSigner xadesSigner){
        this.xadesSigner =xadesSigner;
    }

    @Override
    public String sign(String xmlPath, String signXmlPath) {
        
        try {
            xadesSigner.signXml(xmlPath, signXmlPath, "src/main/resources/static/signature/yourDigitalSign.p12", "yourSuperSecretPassword");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signXmlPath;
    }
    
}
