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

    /**
     * Export state of statistic's calculators to the Json file
     * Currently, this Json file is stored in the server
     * Possible to return it to an user
     *
     * @param path  Path to the file for export
     * @return  {@link org.springframework.http.HttpStatus#OK}
     */
    @GetMapping("/api/export")
    ResponseEntity exportState(@RequestParam String path) {
        stateService.exportState(path);
        return ResponseEntity.ok().build();
    }

    /**
     * Import state of statistic's calculators from the Json file
     * Currently, this Json file is stored in the server
     * Possible to upload that file from an user
     *
     * @param path  Path to the file for import
     * @return  {@link org.springframework.http.HttpStatus#OK}
     */
    @GetMapping("/api/import")
    ResponseEntity importState(@RequestParam String path) {
        stateService.importState(path);
        return ResponseEntity.ok().build();
    }

}
