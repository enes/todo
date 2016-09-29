package com.todo.component;

import org.springframework.util.DigestUtils;

public final class EncyptPassword {

    public static String getMd5(String value) {
        return DigestUtils.md5DigestAsHex(value.getBytes());
    }
}
