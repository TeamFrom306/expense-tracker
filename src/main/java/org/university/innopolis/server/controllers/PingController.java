package org.university.innopolis.server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "/api")
public class PingController {

    @GetMapping(path = "/ping")
    ResponseEntity<?> ping() {
        return ResponseEntity.ok().build();
    }
}
