package me.icwj.homesystem.utilities;

public class CredentialValidator {

    public static boolean isInvalid(final String... values) {
        for (String value : values) {
            if (value == null || value.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}