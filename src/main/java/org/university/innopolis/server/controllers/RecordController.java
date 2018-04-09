package org.university.innopolis.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CurrencyEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.university.innopolis.server.model.Currency;
import org.university.innopolis.server.model.Type;
import org.university.innopolis.server.persistence.RecordRepository;
import org.university.innopolis.server.services.RecordService;

import java.util.Date;

@Controller
@RequestMapping(path="api/user")
public class RecordController {

    private RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping(path="/add/expense")
    ResponseEntity addExpense(@RequestParam String description,
                              @RequestParam int amount,
                              @RequestParam Currency currency,
                              @RequestParam Date date,
                              @RequestParam Type type) {
        return ResponseEntity.ok(recordService.addExpense(description, amount,
                currency, date, type));
    }
}
