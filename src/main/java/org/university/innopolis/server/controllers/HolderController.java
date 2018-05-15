package org.university.innopolis.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.university.innopolis.server.services.AccountService;
import org.university.innopolis.server.services.AuthenticationService;
import org.university.innopolis.server.services.HolderService;
import org.university.innopolis.server.views.HolderView;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "/api")
public class HolderController {
    private HolderService holderService;
    private AuthenticationService authService;

    private static Logger logger = LoggerFactory.getLogger(HolderController.class);

    @Autowired
    public HolderController(HolderService holderService,
                            AuthenticationService authService,
                            AccountService accountService) {
        this.holderService = holderService;
        this.authService = authService;
    }

    /**
     * Get most important data of Holder
     * @param login     for which data will be shown
     *                  User can view only itself data
     * @param holderId  added by AuthFilter
     *                  This method requires authorization from
     *                      {@link org.university.innopolis.server.filters.AuthFilter}
     * @return  {@link HolderView}
     */
    @GetMapping(path = "/user")
    ResponseEntity getHolder(@RequestParam String login,
                             @RequestAttribute int holderId) {
        String logString = "/user, holder: {}, login: {}, status: {}";

        if (!authService.isAuthorized(holderId, login)) {
            logger.debug(logString, holderId, login, HttpStatus.FORBIDDEN);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        HolderView res = holderService.findHolder(login);
        if (res == null) {
            logger.debug(logString, holderId, login, HttpStatus.NOT_FOUND);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this login is not found");
        }
        logger.debug(logString, holderId, login, HttpStatus.OK);
        return ResponseEntity.ok(res);
    }
}
