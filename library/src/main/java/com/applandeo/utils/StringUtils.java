package com.applandeo.utils;

/**
 * Created by Mateusz Kornakiewicz on 30.08.2017.
 */

public class StringUtils {

    /**
     * Validate String if it's null or empty
     *
     * @param text Text to validate
     * @return Validate response
     */
    public static boolean isNullOrEmpty(String text) {
        return text == null || text.isEmpty();
    }
}
