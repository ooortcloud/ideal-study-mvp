package com.idealstudy.mvp.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.security.dto.UserLoginResponseDto;
import com.idealstudy.mvp.helper.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Deprecated
@Slf4j(topic = "로그인 필터")
public class BasicLoginAuthenticationFilter extends BasicAuthenticationFilter {

    private final JwtHelper jwtUtil;

    public BasicLoginAuthenticationFilter(AuthenticationManager authenticationManager, JwtHelper jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              Authentication authResult) throws IOException {
        log.info("success login");
        // Many of the authentication providers will create a UserDetails object as the principal.
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        String username = userDetails.getUsername();
        Role role = Role.stringToRole(
                userDetails.getAuthorities().stream().findFirst()
                        .map(GrantedAuthority::getAuthority)
                        .orElseThrow(() -> new IllegalStateException("No authority found"))
        );

        issueJwtToken(username, role, response);
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException failed) throws IOException {
        log.info("failed login");
        super.onUnsuccessfulAuthentication(request, response, failed);
    }

    private void issueJwtToken(String username, Role role, HttpServletResponse response) throws IOException {

        String token = jwtUtil.createToken(username, role);
        jwtUtil.addJwtToCookie(token, response);

        UserLoginResponseDto responseDto = UserLoginResponseDto.create(username, token);
        // Sets the content type of the response being sent to the client, if the response has not been committed yet.
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        /*
         Sets the character encoding (MIME charset) of the response being sent to the client.
         If the character encoding has already been set by setContentType(java.lang.String)
         or setLocale(java.util.Locale), this method overrides it.
         */
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        ObjectMapper objectMapper = new ObjectMapper();

        // Writes a string.
        response.getWriter().write(objectMapper.writeValueAsString(responseDto));

        // Flushes the stream.
        response.getWriter().flush();
    }
}
