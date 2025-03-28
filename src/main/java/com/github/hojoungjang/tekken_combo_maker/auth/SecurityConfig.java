package com.github.hojoungjang.tekken_combo_maker.auth;

import com.github.hojoungjang.tekken_combo_maker.auth.oauth2.CustomOAuth2UserService;
import com.github.hojoungjang.tekken_combo_maker.member.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
public class SecurityConfig {

    // TODO: Remove in production
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console());
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
                        .requestMatchers(HttpMethod.POST, "/api/v1/members").permitAll()
                        .anyRequest().authenticated());

        http.httpBasic(HttpBasicConfigurer::disable);

        http.formLogin(c -> c
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler)
                        .loginProcessingUrl("/api/v1/auth/login"));

        http.oauth2Login(c -> c
                .userInfoEndpoint(config -> config
                        .userService(customOAuth2UserService)));

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
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    CustomOAuth2UserService customOAuth2UserService(
            PasswordEncoder passwordEncoder,
            MemberRepository memberRepository
    ) {
        return new CustomOAuth2UserService(passwordEncoder, memberRepository);
    }

}
