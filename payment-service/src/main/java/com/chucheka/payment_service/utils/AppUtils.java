package com.chucheka.payment_service.utils;

import java.util.Random;

public class AppUtils {
    public static String generateWalletId() {
        StringBuilder sb = new StringBuilder("00");
        for (int i = 0; i < 8; i++) {
            sb.append(new Random().nextInt(8));
        }
        return sb.toString();
    }
}

