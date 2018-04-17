package org.university.innopolis.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.university.innopolis.server.services.rmi.server.RmiServer;

@SpringBootApplication
public class ApplicationRmi {
    public static void main(String[] args) {
        SpringApplication.run(RmiServer.class, args);
    }
}
