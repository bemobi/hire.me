package com.urlmapping.utils;


import java.security.SecureRandom;


public class URLUtils {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateShortUrlCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    public static String formatTimeTaken(long startTime, int decimalPlaces) {
        long timeTakenMillis = System.currentTimeMillis() - startTime;

        return String.format("%." + decimalPlaces + "f", (double) timeTakenMillis) + "ms";
    }


}
