package util;

import java.util.regex.Pattern;

public class ValidationUtil {
    private static final Pattern COMPTE_PATTERN = Pattern.compile("CPT-\\d{5}");

    public static boolean isValidCompteCode(String code) {
        if (code == null) return false;
        return COMPTE_PATTERN.matcher(code).matches();
    }
}
