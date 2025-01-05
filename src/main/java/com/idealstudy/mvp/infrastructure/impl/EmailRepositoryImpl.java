package com.idealstudy.mvp.infrastructure.impl;

import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.infrastructure.EmailRepository;
import com.idealstudy.mvp.infrastructure.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class EmailRepositoryImpl implements EmailRepository {

    /*
    @Autowired
    private final StringRedisTemplate stringRedisTemplate;

     */

    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;

    private static final long AUTHENTICATION_PERIOD_MINUTE = 30L;

    @Override
    public void addToken(String email, Role role, String token) {

        SignUpDto dto = SignUpDto.builder()
                .token(token)
                .role(role)
                .build();

        redisTemplate.opsForValue().set(email, dto, AUTHENTICATION_PERIOD_MINUTE, TimeUnit.MINUTES);
    }

    @Override
    public SignUpDto getToken(String email) {

        return (SignUpDto) redisTemplate.opsForValue().get(email);
    }

    @Override
    public Boolean deleteToken(String email) {
        return redisTemplate.delete(email);
    }
}
