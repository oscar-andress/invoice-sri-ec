package demo.invoice.sri.signing.infrastructure.certificate;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import demo.invoice.handler.CertificateLoadException;
import demo.invoice.handler.InvalidCertificateException;
import demo.invoice.sri.signing.CertificateData;

public class Pkcs12CertificateLoader implements CertificateLoader{

    @Override
    public CertificateData load(String path, String password) {

        try(FileInputStream fis = new FileInputStream(path)) {

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream(path), password.toCharArray());

            Enumeration<String> aliases = keyStore.aliases();
            if(!aliases.hasMoreElements()) throw new InvalidCertificateException("No alias found in sign certifate");
            String alias = aliases.nextElement();

            PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
            X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);

            if(privateKey == null || cert == null) throw new InvalidCertificateException( "Error loading private key or certificate.");

            return new CertificateData(cert, privateKey);

        } catch (IOException e) {
            throw new CertificateLoadException(e.getMessage(), e.getCause());

        } catch (KeyStoreException | 
                 NoSuchAlgorithmException | 
                 CertificateException |
                 UnrecoverableKeyException e) {
            throw new InvalidCertificateException(e.getMessage(), e.getCause());
        }
    }
    
}
