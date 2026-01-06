package demo.invoice.util;

import java.security.SecureRandom;

public class NumericCodeGeneratorUtil {
    public static String generate() {
        return String.format("%08d", new SecureRandom().nextInt(100_000_000));
    }
}
