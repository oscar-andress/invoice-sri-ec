package demo.invoice.sri.signing.infrastructure.xades;

import demo.invoice.handler.XadesBesProfileException;
import demo.invoice.sri.signing.CertificateData;
import xades4j.production.XadesBesSigningProfile;
import xades4j.production.XadesSigner;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.impl.DirectKeyingDataProvider;
import xades4j.utils.XadesProfileResolutionException;

public class XadesBesProfileFactory implements XadesProfileFactory {

    @Override
    public XadesSigner createSigner(CertificateData data) {

        KeyingDataProvider keyingProvider = 
            new DirectKeyingDataProvider(data.getCertificate(), data.getPrivateKey());
            
        XadesBesSigningProfile profile = new XadesBesSigningProfile(keyingProvider);
        try {
            return profile.newSigner();
        } catch (XadesProfileResolutionException e) {
            throw new XadesBesProfileException(e.getMessage(), e.getCause());
        }
    }
    
}
