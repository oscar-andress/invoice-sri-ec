package demo.invoice.sri.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.invoice.sri.signer.XadesSigner;

@Configuration
public class SignerConfig {

    @Bean
    public XadesSigner xadesSigner() {
        return new XadesSigner();
    }
}