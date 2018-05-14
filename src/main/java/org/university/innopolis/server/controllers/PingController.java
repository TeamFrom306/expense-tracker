package org.university.innopolis.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "/api")
public class PingController {
    private static Logger logger = LoggerFactory.getLogger(PingController.class);

    @GetMapping(path = "/ping")
    ResponseEntity<?> ping() {
        logger.debug("/ping, status: {}", HttpStatus.OK);
        return ResponseEntity.ok().build();
    }
}
