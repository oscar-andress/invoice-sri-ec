package demo.invoice.sri.accessKey.unit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import demo.invoice.sri.accesskey.AccessKeyGenerator;
import demo.invoice.sri.accesskey.SriAccessKeyGenerator;

public class AccessKeyGeneratorTest {
    @Test
    void generateAccessKey_Sucess(){
        AccessKeyGenerator accessKeyGenerator = new SriAccessKeyGenerator();
        
        LocalDate issueDate = LocalDate.of(2026, 1, 8);
        
        // WHEN
        String accessKey = accessKeyGenerator.generate(
                issueDate, 
                "01",
                "9999999999999",
                "1",
                "001",
                "001",
                "000000001",
                "1");

        // THEN
        assertNotNull(accessKey);
    }
}
