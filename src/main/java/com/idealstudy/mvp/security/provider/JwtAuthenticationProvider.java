package com.idealstudy.mvp.security.provider;

import com.idealstudy.mvp.security.token.JwtAuthenticationToken;
import com.idealstudy.mvp.security.userDetailsImpl.JwtDetails;
import com.idealstudy.mvp.helper.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private final JwtHelper jwtUtil;

    // Performs authentication with the same contract as AuthenticationManager.authenticate(Authentication) .
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        JwtDetails jwtDetails = (JwtDetails) token.getDetails();

        try {
            jwtUtil.validateToken(jwtDetails.getToken());
            if( !jwtDetails.isCredentialsNonExpired())
                throw new BadCredentialsException("token이 만료되었습니다.");
        } catch (Exception e) {
            authentication.setAuthenticated(false);
            throw new AuthenticationException(e.getMessage()) {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }

        /*
         Implementations should always allow this method to be called with a false parameter,
         as this is used by various classes to specify the authentication token should not be trusted.
         If an implementation wishes to reject an invocation with a true parameter
         (which would indicate the authentication token is trusted - a potential security risk)
         the implementation should throw an IllegalArgumentException.
         */
        authentication.setAuthenticated(true);
        return authentication;
    }

    // Returns true if this AuthenticationProvider supports the indicated Authentication object.
    @Override
    public boolean supports(Class<?> authentication) {

        return authentication == JwtAuthenticationToken.class;
    }
}
