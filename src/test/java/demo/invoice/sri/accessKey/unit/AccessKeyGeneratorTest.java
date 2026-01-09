package demo.invoice.sri.accessKey.unit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import demo.invoice.sri.accessKey.AccessKeyGenerator;
import demo.invoice.sri.accessKey.SriAccessKeyGenerator;

public class AccessKeyGeneratorTest {
    @Test
    void generateAccessKey_Sucess(){
        AccessKeyGenerator accessKeyGenerator = new SriAccessKeyGenerator();
        
        LocalDateTime issueDate = LocalDateTime.of(2026, 1, 8, 10, 0);
        
        // WHEN
        String accessKey = accessKeyGenerator.generate(
                issueDate, 
                "01",
                "1001122334001",
                "1",
                "001",
                "001",
                "000000001",
                "1");

        // THEN
        assertNotNull(accessKey);
    }
}
