package com.domnian;

import java.security.SecureRandom;
import java.util.Random;

/**
 * ==================================================================
 * Copyright Domnian Dev. (c) 2015. All Rights Reserved
 * Any Code contained within this document, and any associated APIs
 * with similar branding are the sole property of Domnian Dev..
 * Distribution, reproduction, taking snippets, or claiming any
 * contents as your own will break the terms of the license, and
 * void any agreements with you, the third party. Thanks
 * ==================================================================
 */
public class Util {

    public static void success(String msg) {
        System.out.println("\033[7;32m" + msg + "\033[0m");
    }

    public static void severe(String msg) {
        System.err.println("\033[7;31m" + msg + "\033[0m");
    }

    public static void error(String msg) {
        System.err.println("\033[7;33m" + msg + "\033[0m");
    }

    public static void info(String msg) {
        System.out.println("\033[0;37m" + msg + "\033[0m");
    }

    public static void debug(String msg) {
        System.out.println("\033[0;35m" + msg + "\033[0m");
    }

    public static void line() {
        System.out.print("\033[0;36m");
        for ( int i = 0; i < 50; i++ ) {
            System.out.print("=");
        }
        System.out.print("\033[0m");
        System.out.println();
    }

    public static String properCase(String inputVal) {
        if (inputVal.length() == 0) return "";
        if (inputVal.length() == 1) return inputVal.toUpperCase();
        return inputVal.substring(0, 1).toUpperCase() + inputVal.substring(1).toLowerCase();
    }

    public static String randomString(String[] strings) {
        return strings[new Random().nextInt()];
    }

}
