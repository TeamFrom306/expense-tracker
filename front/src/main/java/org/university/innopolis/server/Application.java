package org.university.innopolis.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.university.innopolis.server.filters.AuthFilter;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Autowired
    public FilterRegistrationBean<AuthFilter> jwtFilter(AuthFilter authFilter) {
        final FilterRegistrationBean<AuthFilter> authFilterBean = new FilterRegistrationBean<>();
        authFilterBean.setFilter(authFilter);
        authFilterBean.addUrlPatterns("/api/user/*");
        authFilterBean.addUrlPatterns("/api/balance/*");
        authFilterBean.addUrlPatterns("/api/logout/*");
        authFilterBean.addUrlPatterns("/api/records/*");
        return authFilterBean;
    }
}
