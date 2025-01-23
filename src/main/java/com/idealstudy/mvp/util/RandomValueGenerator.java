package com.idealstudy.mvp.util;

import java.util.UUID;

public final class RandomValueGenerator {

    private RandomValueGenerator() {

    }

    public static String createRandomValue() {
        return UUID.randomUUID().toString();
    }
}
