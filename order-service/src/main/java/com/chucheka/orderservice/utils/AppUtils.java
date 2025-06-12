package com.chucheka.orderservice.utils;

import java.util.UUID;

public class AppUtils {

    public static String generateUniqueCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    }

}
