package com.sengul.biddingapinew.infrastructure.configuration.authorization;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserIdAuthorizationConfiguration {

    @Bean
    public FilterRegistrationBean<UserIdAuthorizationFilter> usernameAuthorizationFilter() {
        FilterRegistrationBean<UserIdAuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserIdAuthorizationFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}