package com.github.hojoungjang.tekken_combo_maker.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hojoungjang.tekken_combo_maker.auth.oauth2.CustomOAuth2UserService;
import com.github.hojoungjang.tekken_combo_maker.member.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    @Value("${WEB_CLIENT_APP_URL}")
    private String webClientOrigin;

    // TODO: Remove in production
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console());
    }

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(webClientOrigin));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "PATCH", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            AuthenticationEntryPoint restLoginAuthenticationEntryPoint,
            AuthenticationSuccessHandler loginSuccessHandler,
            AuthenticationFailureHandler loginFailureHandler,
            CustomOAuth2UserService customOAuth2UserService
    ) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);      // TODO: 제대로 설정해주기

        http.authorizeHttpRequests(c -> c
                .requestMatchers("/swagger", "/swagger-ui/**", "/api-docs/**", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/members").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/characters/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/moves").permitAll()
                .requestMatchers("/img/character/**").permitAll()
                .anyRequest().authenticated());

        http.httpBasic(HttpBasicConfigurer::disable);

        http.formLogin(c -> c
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler)
                        .loginProcessingUrl("/api/v1/auth/login"));

        http.oauth2Login(c -> c
                .userInfoEndpoint(config -> config
                        .userService(customOAuth2UserService))
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
        );

        http.exceptionHandling(c -> c
                .authenticationEntryPoint(restLoginAuthenticationEntryPoint));    // TODO: defaultAuthenticationEntryPointFor() 사용 확인하기

        // TODO: Add session management configuration
        // http.sessionManagement()

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsServiceImpl,
            PasswordEncoder bcryptPasswordEncoder
    ) {
        // Username & password authentication provider
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        daoAuthProvider.setUserDetailsService(userDetailsServiceImpl);
        daoAuthProvider.setPasswordEncoder(bcryptPasswordEncoder);

        return new ProviderManager(daoAuthProvider);
    }

    @Bean
    public UserDetailsService userDetailsServiceImpl() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint restLoginAuthenticationEntryPoint() {
        return new RestLoginAuthenticationEntryPoint();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler(ObjectMapper objectMapper) {
        return new LoginSuccessHandler(objectMapper);
    }

    @Bean
    public AuthenticationFailureHandler loginFailureHandler(ObjectMapper objectMapper) {
        return new LoginFailureHandler(objectMapper);
    }

    @Bean
    CustomOAuth2UserService customOAuth2UserService(
            PasswordEncoder passwordEncoder,
            MemberRepository memberRepository
    ) {
        return new CustomOAuth2UserService(passwordEncoder, memberRepository);
    }

}
