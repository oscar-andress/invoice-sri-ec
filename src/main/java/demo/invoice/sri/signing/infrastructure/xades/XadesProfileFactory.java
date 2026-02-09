package demo.invoice.sri.signing.infrastructure.xades;

import demo.invoice.sri.signing.CertificateData;

public interface XadesProfileFactory {
    xades4j.production.XadesSigner createSigner(CertificateData data);
}
