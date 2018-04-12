package org.university.innopolis.server;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:application-test.properties"})
@ComponentScan(basePackages = {"org.university.innopolis.server"})
public class AppConfig {
}
