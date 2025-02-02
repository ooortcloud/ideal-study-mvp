package com.idealstudy.mvp.infrastructure.impl;

import com.idealstudy.mvp.enums.error.DBErrorMsg;
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
    public SignUpDto addToken(String token, String email, Role role) {

        SignUpDto dto = SignUpDto.builder()
                .email(email)
                .role(role)
                .build();

        redisTemplate.opsForValue().set(token, dto, AUTHENTICATION_PERIOD_MINUTE, TimeUnit.MINUTES);

        return dto;
    }

    @Override
    public SignUpDto getToken(String token) throws IllegalArgumentException {

        Object object = redisTemplate.opsForValue().get(token);

        if(object == null)
            throw new IllegalArgumentException(DBErrorMsg.SELECT_ERROR.getMsg());

        return (SignUpDto) object;
    }

    @Override
    public Boolean deleteToken(String token) {
        return redisTemplate.delete(token);
    }
}
