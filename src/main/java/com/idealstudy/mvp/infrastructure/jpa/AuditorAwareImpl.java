package com.idealstudy.mvp.infrastructure.jpa;

import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    private final HttpServletRequest request;

    @Autowired
    public AuditorAwareImpl(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Optional<String> getCurrentAuditor() {

        JwtPayloadDto dto = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String userId = dto.getSub();
        return Optional.ofNullable(userId);
    }
}
