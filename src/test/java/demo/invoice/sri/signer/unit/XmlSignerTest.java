package demo.invoice.sri.signer.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
            .thenThrow(Exception.class); 

        // Act && THEN
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class, 
            () -> xadesXmlSigner.sign(unsignedXml));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        verify(xadesSigner, times(1)).signXml(unsignedXml, pathCertificate, keyCertificate);
    }

}
