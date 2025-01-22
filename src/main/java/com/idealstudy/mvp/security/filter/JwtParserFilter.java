package com.idealstudy.mvp.security.filter;

import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import com.idealstudy.mvp.helper.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Deprecated
@RequiredArgsConstructor
@Slf4j(topic = "jwt parser")
public class JwtParserFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtHelper jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if(jwtUtil.isTokenExist(request)) {
            JwtPayloadDto payload = jwtUtil.getPayloadFromToken(jwtUtil.getUserInfoFromToken(request));
            request.setAttribute("jwtPayload", payload);
        }

        doFilter(request, response, filterChain);
    }
}
