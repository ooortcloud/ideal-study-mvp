package com.idealstudy.mvp.presentation.controller.component;

import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenComponent {

    private final HttpServletRequest request;

    public String getUserId() {

        JwtPayloadDto dto = (JwtPayloadDto) request.getAttribute("jwtPayload");
        return dto.getSub();
    }

    public Role getRole() {

        JwtPayloadDto dto = (JwtPayloadDto) request.getAttribute("jwtPayload");
        return dto.getRole();
    }
}
