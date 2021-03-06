package org.university.innopolis.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.university.innopolis.server.services.AuthenticationService;
import org.university.innopolis.server.services.exceptions.BadCredentialsException;
import org.university.innopolis.server.services.exceptions.DuplicatedUserException;
import org.university.innopolis.server.views.HolderView;
import org.university.innopolis.server.wrappers.CredentialsWrapper;

import javax.validation.Valid;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "/api")
public class AuthenticationController {
    private AuthenticationService authService;
    private static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/login")
    ResponseEntity login(@Valid @RequestBody CredentialsWrapper wrapper) {
        String logString = "/login, login: {}, password: {}, status: {}";
        try {
            HolderView holder = authService.getAuthentication(wrapper.getLogin(), wrapper.getPassword());
            logger.debug(logString, wrapper.getLogin(), wrapper.getPassword(), HttpStatus.OK);
            return ResponseEntity.ok(holder);
        } catch (BadCredentialsException ignored) {
            logger.debug(logString, wrapper.getLogin(), wrapper.getPassword(), HttpStatus.UNAUTHORIZED);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

    @PostMapping(path = "/register")
    ResponseEntity createHolder(@Valid @RequestBody CredentialsWrapper wrapper) {
        String logString = "/register, login: {}, password: {}, status: {}";
        try {
            HolderView holder = authService.registerHolder(wrapper.getLogin(), wrapper.getPassword());
            logger.debug(logString, wrapper.getLogin(), wrapper.getPassword(), HttpStatus.OK);
            return ResponseEntity.ok(holder);
        } catch (DuplicatedUserException ignored) {
            logger.debug(logString, wrapper.getLogin(), wrapper.getPassword(), HttpStatus.CONFLICT);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This login is already taken");
        } catch (BadCredentialsException e) {
            logger.debug(logString, wrapper.getLogin(), wrapper.getPassword(), HttpStatus.BAD_REQUEST);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid login or password");
        }
    }

    @PostMapping(path = "/logout")
    ResponseEntity logout(@RequestAttribute int holderId) {
        String logString = "/logout, holder: {}, status: {}";
        authService.revokeTokenById(holderId);
        logger.debug(logString, holderId, HttpStatus.OK);
        return ResponseEntity.ok().build();
    }
}
