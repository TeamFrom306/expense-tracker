package org.university.innopolis.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.university.innopolis.server.services.StateService;

@RestController
public class StateController {
    private StateService stateService;

    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping("/api/export")
    ResponseEntity exportState(@RequestParam String path) {
        stateService.exportState(path);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/import")
    ResponseEntity importState(@RequestParam String path) {
        stateService.importState(path);
        return ResponseEntity.ok().build();
    }

}
