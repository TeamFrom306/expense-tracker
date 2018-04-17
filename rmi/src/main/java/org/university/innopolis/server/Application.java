package org.university.innopolis.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.university.innopolis.server.rmi.server.RmiServer;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(RmiServer.class, args);
    }
}
