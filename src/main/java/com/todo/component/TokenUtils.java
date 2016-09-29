package com.todo.component;

import java.util.UUID;

public final class TokenUtils {
    private TokenUtils() {
    }

    public static String generateTokenFrom(Long id) {
        return id.toString() + UUID.randomUUID().toString() + "-" + UUID.randomUUID().toString();
    }
}
