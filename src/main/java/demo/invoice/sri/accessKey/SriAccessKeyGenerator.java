package demo.invoice.sri.accessKey;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import demo.invoice.util.AccessKeyUtil;
import demo.invoice.util.NumericCodeGeneratorUtil;

@Component
public class SriAccessKeyGenerator implements AccessKeyGenerator{

    @Override
    public String generate(LocalDateTime issueDate,
                           String receipType, 
                           String ruc, 
                           String enviroment, 
                           String establishmentCode,
                           String emissionPointCode,
                           String nextInvoiceSequential,
                           String emissionType) {
        return AccessKeyUtil.generateAccessKey(    
                    issueDate,                                                                   // Fecha de emisión
                    receipType,                                                        // Tipo comprobante (factura)
                    ruc,                                                              // RUC del emisor
                    enviroment,                                                // Ambiente: 1=pruebas, 2=producción
                    establishmentCode + emissionPointCode,    // Serie: establecimiento+puntoEmision
                    nextInvoiceSequential,                                                       // Secuencial
                    NumericCodeGeneratorUtil.generate(),                                         // Código numérico (aleatorio o incremental)
                    emissionType                                               // Tipo de emisión
                );
    }
    
}
