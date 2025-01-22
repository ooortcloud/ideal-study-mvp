package com.idealstudy.mvp.security.filter;

import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import com.idealstudy.mvp.security.token.JwtAuthenticationToken;
import com.idealstudy.mvp.security.userDetailsImpl.JwtDetails;
import com.idealstudy.mvp.helper.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component
@Slf4j(topic = "JWT 토큰 필터(Not OAuth2.0)")
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private final JwtHelper jwtUtil;

    public JwtAuthenticationFilter(JwtHelper jwtUtil) {
        // request 보안 설정이 우선 적용되고, permitAll이 아닌 나머지에 대해서 filter에 걸리기를 기대함.
        super("/api/**");
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        log.info("JWT 토큰 검사 진행");
        try{
            String token = jwtUtil.getUserInfoFromToken(request);
            JwtPayloadDto payload = jwtUtil.getPayloadFromToken(token);

            JwtAuthenticationToken authToken = buildJwtAuthenticationToken(token, payload);
            return getAuthenticationManager().authenticate(authToken);
        } catch (Exception e) {
            log.error(e.toString() + " : " + e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        log.info("인증 성공!");

        JwtPayloadDto payload = jwtUtil.getPayloadFromToken(jwtUtil.getUserInfoFromToken(request));
        request.setAttribute("jwtPayload", payload);

        SecurityContextHolder.getContext().setAuthentication(authResult);

        /*
         Causes the next filter in the chain to be invoked, or if the calling filter is the last filter in the chain,
         causes the resource at the end of the chain to be invoked.
         */
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

    private JwtAuthenticationToken buildJwtAuthenticationToken(String token, JwtPayloadDto payload) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(payload.getRole().toString());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        JwtAuthenticationToken authToken = new JwtAuthenticationToken(authorities);
        authToken.setDetails(new JwtDetails(token, payload));
        return authToken;
    }
}
