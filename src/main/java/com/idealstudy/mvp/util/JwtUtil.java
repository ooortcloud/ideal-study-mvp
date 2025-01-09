package com.idealstudy.mvp.util;

import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j(topic = "JwtUtil")
public class JwtUtil {

    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "role";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    private static final long TOKEN_TIME = 60 * 60 * 1000L; // 1시간
    private static final SecretKey key = Jwts.SIG.HS256.key().build();

    /*
    @PostConstruct // 한 번만 받아와도 되는 값을 사용할때마다 요청을 새로하지 않기 위해
    public void init() {
        key = alg.key().build();
    }
     */

    @Deprecated
    public String createToken(String username, Role role) {
        Date date = new Date();
        try{
            String token = Jwts.builder()
                    .subject(username) // 사용자 식별자값(ID)
                    .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                    .expiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                    .issuedAt(date) // 발급일
                    .signWith(key) // 암호화 알고리즘
                    .compact();

            validateToken(token);

            return BEARER_PREFIX + token;

        } catch (Exception e) {
            log.error(e + " : " + e.getMessage());
            throw e;
        }
    }

    // 1. JWT 토큰 생성 (1) JWT 토큰을 헤더에 달아 보낼수도 있고 (2) 쿠키객체에 담아 줄 수도 있다 - 프론트와 조율해야함
    public String createToken(MemberDto dto) throws Exception {
        Date date = new Date();
        log.info("발급시간: " + date.toString());
        try{
            log.info("JWT 토큰 생성");
            String token = Jwts.builder()
                    .header().type("JWT").and()  // typ
                    .subject(dto.getUserId()) // 사용자 식별자값(ID)
                    .claim(AUTHORIZATION_KEY, dto.getRole().toString()) // 사용자 권한
                    .claim("name", dto.getName())
                    .claim("level", dto.getLevel())
                    .expiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                    .issuedAt(date) // 발급일
                    .signWith(key) // signature
                    .compact();

            /*
            log.info("생성 시 사용된 키: " + Encoders.BASE64.encode(key.getEncoded()));
            log.info("생성된 토큰값: " + token);
             */

            validateToken(token);
            
            return BEARER_PREFIX + token;

        } catch (Exception e) {
            log.error(e + " : " + e.getMessage());
            throw e;
        }
    }

    // 2. jWT를 쿠키에 저장
    @Deprecated
    public void addJwtToCookie(String token, HttpServletResponse res) {
        token = URLEncoder.encode(token, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
        cookie.setPath("/");
        // 쿠키에 만료시간 추가
        cookie.setMaxAge((int) (TOKEN_TIME / 1000L));
        cookie.setHttpOnly(true); // XSS 방지

        // Response 객체에 Cookie 추가
        res.addCookie(cookie);
    }

    // 4. JWT 검증
    public boolean validateToken(String token) {
        try {
            log.info("생성된 토큰 검증");
            /*
            log.info("검증 시 사용된 키: " + Encoders.BASE64.encode(key.getEncoded()));
            log.info("검증할 토큰값: " + token);
             */

            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        } catch (Exception e) {
            log.error(e + " : " + e.getMessage());
        }
        return false;
    }

    // 5. JWT에서 사용자 정보 가져오기
    @Deprecated
    public Claims getUserInfoFromToken(String token) {
        token = substringToken(token);

        if(validateToken(token)) {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        } else {
            return null;
        }
    }

    public boolean isTokenExist(HttpServletRequest req) {
        String token = req.getHeader(AUTHORIZATION_HEADER);
        return !(token == null);
    }

    // Http Header에서 JwtToken의 payload를 가져옴.
    public String getUserInfoFromToken(HttpServletRequest req) {
        return substringToken(req.getHeader(AUTHORIZATION_HEADER));
    }

    public String getAuthorizationHeader(HttpServletRequest req) {
        return req.getHeader(AUTHORIZATION_HEADER);
    }

    public JwtPayloadDto getPayloadFromToken(String token) {

        validateToken(token);
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

        return  JwtPayloadDto.builder()
                .sub(claims.getSubject())
                .role(Role.stringToRole(claims.get(AUTHORIZATION_KEY, String.class)))
                .name(claims.get("name", String.class))
                .level(claims.get("level", Integer.class))
                .exp(claims.getExpiration())
                .iat(claims.getIssuedAt())
                .build();
    }

    // 3. Cookie 의 Value에 있던 JWT 토큰을 Substring(자른다)
    private String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        log.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }


}
