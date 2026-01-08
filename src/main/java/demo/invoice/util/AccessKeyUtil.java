package demo.invoice.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccessKeyUtil {
    public static String generateAccessKey(Date fechaEmision, String tipoComprobante, String ruc,
                                            String ambiente, String serie, String secuencial,
                                            String codigoNumerico, String tipoEmision) {
        // Fecha en formato ddMMyyyy
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        String fecha = sdf.format(fechaEmision);

        // Concatenar los campos
        StringBuilder sb = new StringBuilder();
        sb.append(fecha);             // Fecha emisión
        sb.append(tipoComprobante);   // Tipo comprobante: 01 = factura
        sb.append(ruc);               // RUC emisor
        sb.append(ambiente);          // Ambiente: 1=pruebas, 2=producción
        sb.append(serie);             // Serie = establecimiento(3) + puntoEmision(3)
        sb.append(secuencial);        // Secuencial (9 dígitos)
        sb.append(codigoNumerico);    // Código numérico (8 dígitos o más)
        sb.append(tipoEmision);       // Tipo de emisión: 1 = normal

        // Calcular dígito verificador (módulo 11)
        String base = sb.toString();
        int digitoVerificador = calculateDigitVerifier(base);
        return base + digitoVerificador;
    }

    private static int calculateDigitVerifier(String clave) {
        int[] pesos = {2, 3, 4, 5, 6, 7};
        int suma = 0;
        int pesoIndex = 0;

        for (int i = clave.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(clave.charAt(i));
            suma += digit * pesos[pesoIndex];
            pesoIndex = (pesoIndex + 1) % pesos.length;
        }

        int mod11 = 11 - (suma % 11);
        if (mod11 == 11) return 0;
        if (mod11 == 10) return 1;
        return mod11;
    }
}
