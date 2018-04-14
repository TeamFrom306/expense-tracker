package org.university.innopolis.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.university.innopolis.server.filters.AuthFilter;
import org.university.innopolis.server.model.Account;
import org.university.innopolis.server.model.Category;
import org.university.innopolis.server.model.Record;

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
        authFilterBean.addUrlPatterns("/api/logout/*");
        authFilterBean.addUrlPatterns("/api/expenses");
        authFilterBean.addUrlPatterns("/api/incomes");
        return authFilterBean;
    }
}
