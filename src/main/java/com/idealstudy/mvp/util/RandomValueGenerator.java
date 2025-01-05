package com.idealstudy.mvp.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RandomValueGenerator {

    public String createRandomValue() {
        return UUID.randomUUID().toString();
    }
}
