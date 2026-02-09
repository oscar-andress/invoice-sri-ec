package demo.invoice.sri.signing.infrastructure.certificate;

import demo.invoice.sri.signing.CertificateData;

public interface CertificateLoader {
    CertificateData load(String path, String password);
}
