package com.idealstudy.mvp.security.filter;

import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import com.idealstudy.mvp.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenGrepper extends OncePerRequestFilter {

    @Autowired
    private final JwtUtil jwtUtil;

    /**
     * 이 필터는 비회원 접근 가능한 서비스에서 token 정보를 필요로 하는 경우 Payload 정보를 넘겨주기 위해 구현되었다.
     * 대표적인 예시로 로그인한 사용자가 자신의 비밀 글에 접근하는 경우에 이 필터가 반드시 필요하다.
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String rawToken = jwtUtil.getAuthorizationHeader(request);
        // log.info("Is authorization header NULL? " + rawToken);
        if(rawToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Attribute 안에 이미 jwtPayload가 있으면 넘기고, 없으면 넣어주자.
        if(request.getAttribute("jwtPayload") == null) {
            
            log.info("토큰을 들고 있는 회원 인식됨");
            String token = jwtUtil.getUserInfoFromToken(request);
            JwtPayloadDto dto = jwtUtil.getPayloadFromToken(token);
            request.setAttribute("jwtPayload", dto);
        }

        filterChain.doFilter(request, response);
    }
}
