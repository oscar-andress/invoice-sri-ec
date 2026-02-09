package demo.invoice.sri.signing;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor

public class CertificateData {
    private final X509Certificate certificate;
    private final PrivateKey privateKey;    
}
