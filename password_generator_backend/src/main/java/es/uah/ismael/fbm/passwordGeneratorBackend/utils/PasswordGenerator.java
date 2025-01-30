package es.uah.ismael.fbm.passwordGeneratorBackend.utils;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PasswordGenerator {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIALS = "#%&*-_=+;:,.?";

    public static String generatePassword(int length, boolean useUpper, boolean useNumbers, boolean useSpecials) {
        String charPool = LOWERCASE;
        if (useUpper) charPool += UPPERCASE;
        if (useNumbers) charPool += NUMBERS;
        if (useSpecials) charPool += SPECIALS;

        SecureRandom random = new SecureRandom();
        String finalCharPool = charPool;
        String collect = IntStream.range(0, length)
                .mapToObj(i -> String.valueOf(finalCharPool.charAt(random.nextInt(finalCharPool.length()))))
                .collect(Collectors.joining());
        return collect;
    }
}
