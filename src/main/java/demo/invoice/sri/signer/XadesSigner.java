package demo.invoice.sri.signer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import java.util.Enumeration;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Transformer;

import org.w3c.dom.Document;
import xades4j.algorithms.EnvelopedSignatureTransform;
import xades4j.production.BasicSignatureOptions;
import xades4j.production.DataObjectReference;
import xades4j.production.SignedDataObjects;
import xades4j.production.XadesBesSigningProfile;
import xades4j.properties.DataObjectDesc;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.impl.DirectKeyingDataProvider;

public class XadesSigner {
    
    public void signXml(String pathXml, String pathFirmado, String pathCertificado, String clave) throws Exception {
        // Cargar el certificado PKCS12
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(new FileInputStream(pathCertificado), clave.toCharArray());

        Enumeration<String> aliases = keyStore.aliases();
        String alias = aliases.nextElement();

        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, clave.toCharArray());
        X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);

        // Crear proveedor directo (sin KeyStore intermedio)
        KeyingDataProvider keyingProvider = new DirectKeyingDataProvider(cert, privateKey);
            
        // Crear perfil de firma XAdES-BES
        XadesBesSigningProfile profile = (XadesBesSigningProfile) new XadesBesSigningProfile(keyingProvider)
            .withBasicSignatureOptions(new BasicSignatureOptions().checkKeyUsage(false));
        xades4j.production.XadesSigner signer = profile.newSigner();

        // Leer documento XML
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = dbf.newDocumentBuilder().parse(new FileInputStream(pathXml));

        // Firmar XML
        DataObjectDesc obj = new DataObjectReference("#" + doc.getDocumentElement().getAttribute("id"))
            .withTransform(new EnvelopedSignatureTransform());

        doc.getDocumentElement().setIdAttribute("id", true);
        signer.sign(new SignedDataObjects(obj), doc.getDocumentElement());

        // REMOVER atributo Id (¡para que el SRI lo acepte!)
        doc.getDocumentElement().setIdAttribute("id", false);

        // Guardar resultado
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(pathFirmado)));

        System.out.println("✅ XML firmado correctamente en: " + pathFirmado);
    }
}
