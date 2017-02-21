package com.universal.commons.util;

public class StarLevelUtils {

    private static final int L1 = 299;
    private static final int L2 = 1999;
    private static final int L3 = 9999;
    private static final int L4 = 19999;

    public static int calculate(int score) {

        if (score <= L1) {
            return 1;
        } else if (score <= L2) {
            return 2;
        } else if (score <= L3) {
            return 3;
        } else if (score <= L4) {
            return 4;
        } else {
            return 5;
        }

    }

}
