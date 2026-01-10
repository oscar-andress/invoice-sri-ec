package demo.invoice.sri.signer.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import demo.invoice.sri.signer.XadesSigner;
import demo.invoice.sri.signer.XadesXmlSigner;

@ExtendWith(MockitoExtension.class)
class XmlSignerTest {
    @Mock
    private XadesSigner xadesSigner;

    @InjectMocks
    private XadesXmlSigner xadesXmlSigner;

    @Test
    void sign_ThrowsException_SignNotExist() throws Exception{
        // GIVEN
        String unsignedXml = "<xml>unsigned</xml>";
        String pathCertificate = "src/main/resources/static/signature/yourSign.p12";
        String keyCertificate = "yourSuperSecretKey";
         
        // WHEN
        when(xadesSigner.signXml(unsignedXml, pathCertificate, keyCertificate))
            .thenReturn(""); 

        // Act && THEN
        String signedXml = xadesXmlSigner.sign(unsignedXml);

        assertEquals("", signedXml);
        verify(xadesSigner, times(1)).signXml(unsignedXml, pathCertificate, keyCertificate);
    }

}
