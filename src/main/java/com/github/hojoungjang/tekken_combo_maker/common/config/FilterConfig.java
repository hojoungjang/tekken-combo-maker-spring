package com.github.hojoungjang.tekken_combo_maker.common.config;

import com.github.hojoungjang.tekken_combo_maker.common.filter.InternalClientAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Value("${application.env.rest-client-auth-key}")
    private String authKey;

    @Bean
    public FilterRegistrationBean<InternalClientAuthFilter> internalClientAuthFilter() {
        FilterRegistrationBean<InternalClientAuthFilter> registrationBean = new FilterRegistrationBean<>();

        InternalClientAuthFilter filter = new InternalClientAuthFilter(authKey);

        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
}
