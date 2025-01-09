package com.idealstudy.mvp.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.security.userDetailsImpl.MemberDetails;
import com.idealstudy.mvp.security.dto.UserLoginRequestDto;
import com.idealstudy.mvp.security.dto.UserLoginResponseDto;
import com.idealstudy.mvp.util.JwtUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Slf4j
public class JsonLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private final JwtUtil jwtUtil;

    @PostConstruct
    void setup() {
        // Sets the URL that determines if authentication is required
        setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("attempt authentication");
        log.info("form content type : " + request.getContentType());
        try {
            UserLoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
                    UserLoginRequestDto.class);
            log.info(requestDto.toString());

            return getAuthenticationManager().authenticate(

                    /*
                     This constructor can be safely used by any code that wishes to create
                     a UsernamePasswordAuthenticationToken, as the AbstractAuthenticationToken.isAuthenticated()
                     will return false.
                     */
                    new UsernamePasswordAuthenticationToken(
                            requestDto.username(),
                            requestDto.password(),
                            null
                    )
            );
            // TODO : 필터 레벨에서 동일한 양식의 에러 응답을 하려면 에러 처리 필터를 만든 후, 필터 순서를 가장 앞에 둔 다음,
            //  직접 ObjectMapper로 json으로 직렬화 하시면 됩니다..!
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        log.info("인증 성공. 인가 작업 수행");

        // Many of the authentication providers will create a UserDetails object as the principal.
        MemberDetails userDetails = (MemberDetails) authResult.getPrincipal();
        MemberDto dto = userDetails.getMemberDto();
        try {
            issueJwtToken(dto, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        log.info("인증 실패.");

        // Sets the status code for this response.
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        throw new AccessDeniedException("인증에 실패하였습니다.");
    }

    private void issueJwtToken(MemberDto dto, HttpServletResponse response) throws Exception {

        String token = jwtUtil.createToken(dto);

        UserLoginResponseDto responseDto = UserLoginResponseDto.create(dto.getUserId(), token);
        // Sets the content type of the response being sent to the client, if the response has not been committed yet.
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        /*
         Sets the character encoding (MIME charset) of the response being sent to the client.
         If the character encoding has already been set by setContentType(java.lang.String)
         or setLocale(java.util.Locale), this method overrides it.
         */
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        response.setHeader("Authorization", token);

        ObjectMapper objectMapper = new ObjectMapper();

        // Writes a string.
        response.getWriter().write(objectMapper.writeValueAsString(responseDto));

        // Flushes the stream.
        response.getWriter().flush();
    }
}
