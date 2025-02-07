package com.idealstudy.mvp.config;

import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.security.filter.*;
import com.idealstudy.mvp.security.provider.JwtAuthenticationProvider;
import com.idealstudy.mvp.helper.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.annotation.AnnotationTemplateExpressionDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Log4j2
@RequiredArgsConstructor
public class SecurityConfig {

    // TODO: 추후 Spring Security에서 제공하는 Jwt 라이브러리로 대체될 수 있음
    @Autowired
    private final JwtHelper jwtUtil;
    @Autowired
    private final UserDetailsService userDetailsService;

    @Value("${server.dev}")
    private String isDev;

    @Value("${server.https}")
    private String isHttps;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        List<AuthenticationProvider> providers = new ArrayList<>();

        // UsernamePasswordAuthenticationToken 처리기
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        providers.add(daoAuthenticationProvider);

        // JWT token 처리기(Not OAuth2.0 token)
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtUtil);
        providers.add(jwtAuthenticationProvider);
        return new ProviderManager(providers);
    }

    /*
    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilter() {
        return new ExceptionHandlerFilter();
    }

     */

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        // CORS 요청을 허용할 출처
        config.setAllowedOrigins(Arrays.asList("http://3.38.162.79:3000", "https://www.idealstudy.store", "http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // CORS 요청을 허용할 메서드
        config.setAllowCredentials(true); // CORS 쿠키나 인증정보를 포함한 요청 허용
        config.setAllowedHeaders(Collections.singletonList("*")); // CORS 요청을 허용할 헤더
        config.setMaxAge(3600L); // pre-flight 요청을 캐싱할 1시간

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public JsonLoginAuthenticationFilter jsonLoginAuthenticationFilter() {
        JsonLoginAuthenticationFilter filter = new JsonLoginAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(userDetailsService, passwordEncoder()));
        return filter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(null, null));
        return filter;
    }

    @Bean
    public JwtTokenGrepper jwtTokenGrepper() {
        JwtTokenGrepper filter = new JwtTokenGrepper(jwtUtil);
        return filter;
    }

    // @Bean
    public JwtParserFilter jwtParserFilter() {
        JwtParserFilter filter = new JwtParserFilter(jwtUtil);
        return filter;
    }

    @Bean
    static RoleHierarchy roleHierarchy() {

        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role(Role.ROLE_STUDENT.toString()).implies(Role.ROLE_GUEST.toString())
                .role(Role.ROLE_TEACHER.toString()).implies(Role.ROLE_GUEST.toString())
                .role(Role.ROLE_PARENTS.toString()).implies(Role.ROLE_GUEST.toString())
                .role(Role.ROLE_ADMIN.toString()).implies(
                        Role.ROLE_STUDENT.toString(), Role.ROLE_STUDENT.toString(), Role.ROLE_PARENTS.toString())
                .build();
    }

    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {

        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }

    @Bean
    static AnnotationTemplateExpressionDefaults templateExpressionDefaults() {
        return new AnnotationTemplateExpressionDefaults();
    }

    // @Bean
    public BasicLoginAuthenticationFilter loginAuthenticationFilter() {
        BasicLoginAuthenticationFilter filter = new BasicLoginAuthenticationFilter(authenticationManager(
                userDetailsService, passwordEncoder()), jwtUtil);

        return filter;
    }

    // @Bean
    public FormLoginAuthenticationFilter formLoginAuthenticationFilter() {
        FormLoginAuthenticationFilter filter = new FormLoginAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(userDetailsService, passwordEncoder()));
        return filter;
    }

    // TODO: OAuth2LoginAuthenticationFilter 생성 및 설정 필요


    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {

        // Enables CSRF protection. This is activated by default when using EnableWebSecurity.
        // CSRF token 사용 시 POST 요청만 가능.
        http.csrf(AbstractHttpConfigurer::disable);

        // Disable HTTP Basic authentication.
        // Disable Form type login.
        http.httpBasic(AbstractHttpConfigurer::disable)
               .formLogin(AbstractHttpConfigurer::disable);

        // configuring of Session Management: stateless
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // https only
        setHsts(http);

        setCors(http);

        // 전체 권한 open  << 메소드 레벨에서 권한 처리함
        setAuthorizeHttpRequests(http);

        // 로그아웃 설정(추후 변경 필요)
        setLogout(http);

        registerFilters(http);

        return http.build();
    }

    private void setHsts(HttpSecurity http) throws Exception {
        if(isDev.equals("true") || isHttps.equals("false")) {
            http.headers(headers -> headers
                    // Disables Strict Transport Security
                    .httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable)
            );
        }
    }

    private void setCors(HttpSecurity http) throws Exception {

        http.cors(Customizer.withDefaults());

        // 매 요청마다 CorsConfiguration 객체를 생성하는 비정상적인 로직
        /*
        http.cors(customizer -> customizer.configurationSource( // HttpServletRequest를 기반으로 CORS 설정을 반환
                new CorsConfigurationSource() { // CORS 설정을 직접 정의하는 익명 클래스
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) { // CORS 정책을 정의
                        // corsConfiguration 객체를 생성하여 CORS 설정을 담을 컨테이너로 사용
                        CorsConfiguration config = new CorsConfiguration();
                        // CORS 요청을 허용할 출처
                        config.setAllowedOrigins(Arrays.asList("http://3.38.162.79:3000", "https://www.idealstudy.store", "http://localhost:3000"));
                        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // CORS 요청을 허용할 메서드
                        config.setAllowCredentials(true); // CORS 쿠키나 인증정보를 포함한 요청 허용
                        config.setAllowedHeaders(Collections.singletonList("*")); // CORS 요청을 허용할 헤더
                        config.setMaxAge(3600L); // pre-flight 요청을 캐싱할 1시간
                        return config;
                    }
                }
        ));

         */
    }


    private void setAuthorizeHttpRequests(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth ->
                auth
                        .anyRequest().permitAll()
        );
    }

    private void setLogout(HttpSecurity http) throws Exception {
        /*
         The default is that accessing the URL "/logout" will log the user out by invalidating the HTTP Session,
         cleaning up any rememberMe() authentication that was configured,
         clearing the SecurityContextHolder, and then redirect to "/login?success".
         */
        http.logout(logout -> logout.deleteCookies("JSESSIONID")
                .invalidateHttpSession(false)
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/login?success")
        );
    }

    private void registerFilters(HttpSecurity http) {
        /*
         Allows adding a Filter before one of the known Filter classes.
         The known Filter instances are either a Filter listed in HttpSecurityBuilder.addFilter(Filter)
         or a Filter that has already been added using HttpSecurityBuilder.addFilterAfter(Filter, Class)
         or HttpSecurityBuilder.addFilterBefore(Filter, Class).
         */
        // http.addFilterBefore(exceptionHandlerFilter(), LogoutFilter.class);
        // http.addFilterBefore(loginAuthenticationFilter(), BasicAuthenticationFilter.class);
        // http.addFilterBefore(formLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jsonLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtAuthenticationFilter(), JsonLoginAuthenticationFilter.class);
        http.addFilterAfter(jwtTokenGrepper(), JwtAuthenticationFilter.class);
    }

    private void setMetadataPermission(HttpSecurity http) throws Exception {
        // Allows restricting access based upon the HttpServletRequest using RequestMatcher implementations
        // (i.e. via URL patterns).
        http.authorizeHttpRequests(auth ->
                auth
                        // 정적 파일 허용
                        // PathRequest: Factory that can be used to create a RequestMatcher for commonly used paths.
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(HttpMethod.GET, "/favicon.ico").permitAll()
        );
    }

    private void setGuestPermission(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // This matcher will use the same rules that Spring MVC uses for matching.
                .requestMatchers(HttpMethod.GET, "/").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/offcialProfile/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/sign-up").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users/email-authentication").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users").permitAll()
        );
    }

    private void setTestPermission(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/create-dummies").permitAll()
        );
    }

    @Deprecated
    private void setUserPermission(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // the roles that the user should have at least one of (i.e. ADMIN, USER, etc).
                // Each role should not start with ROLE_ since it is automatically prepended already
                .requestMatchers(HttpMethod.POST, "/auth/logout").hasAnyRole(Role.ROLE_ADMIN.toString(),
                        Role.ROLE_STUDENT.toString(),
                        Role.ROLE_TEACHER.toString())
                .requestMatchers(HttpMethod.GET, "/api/users/*").hasAnyRole(Role.ROLE_ADMIN.toString(),
                        Role.ROLE_STUDENT.toString(),
                        Role.ROLE_TEACHER.toString())
                .requestMatchers(HttpMethod.PATCH, "/api/users/*").hasAnyRole(Role.ROLE_ADMIN.toString(),
                        Role.ROLE_STUDENT.toString(),
                        Role.ROLE_TEACHER.toString())
                .requestMatchers(HttpMethod.GET, "/api/mypage/**").hasAnyRole(Role.ROLE_ADMIN.toString(),
                        Role.ROLE_STUDENT.toString(),
                        Role.ROLE_TEACHER.toString())
                .requestMatchers(HttpMethod.GET, "/api/mypage/**").hasAnyRole(Role.ROLE_ADMIN.toString(),
                        Role.ROLE_STUDENT.toString(),
                        Role.ROLE_TEACHER.toString())
        );
    }

    @Deprecated
    private void setStudentPermission(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/student/**").hasRole(Role.ROLE_STUDENT.toString())

        );
    }

    @Deprecated
    private void setTeacherPermission(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/oficialProfile/*").hasRole(Role.ROLE_TEACHER.toString())
        );
    }

    @Deprecated
    private void setAdminPermission(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/users").hasRole(Role.ROLE_ADMIN.toString())
                .requestMatchers(HttpMethod.DELETE, "/api/users/*").hasRole(Role.ROLE_ADMIN.toString())
               // .requestMatchers(HttpMethod.GET, "/swagger-ui.html", "/v3/api-docs").hasRole(Role.ROLE_ADMIN.toString())
                /// TODO: 나중에 Admin 추가할 때 같이 풀 것.
        );
    }
}
