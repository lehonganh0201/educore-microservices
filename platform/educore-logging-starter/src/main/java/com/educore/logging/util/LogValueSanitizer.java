package com.educore.logging.util;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 10:15
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class LogValueSanitizer {
    private LogValueSanitizer() {
        throw new IllegalStateException(
                "Utility class must not be instantiated"
        );
    }

    public static String sanitize(String value) {
        if (value == null) {
            return null;
        }

        StringBuilder result =
                new StringBuilder(value.length());

        for (int index = 0;
             index < value.length();
             index++) {

            char character = value.charAt(index);

            if (Character.isISOControl(character)) {
                result.append('_');
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }
}
