package org.university.innopolis.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.services.RecordService;
import org.university.innopolis.server.services.exceptions.WrongDateParameterException;
import org.university.innopolis.server.views.RecordView;

import java.util.Date;

@Controller
@RequestMapping(path="api/")
public class RecordController {

    private RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping(path="/add/expense")
    ResponseEntity addExpense(@RequestParam String description,
                              @RequestParam double amount,
                              @RequestParam Currency currency,
                              @RequestParam String date) {
        try {
            RecordView res = recordService.addRecord(
                    description,
                    amount,
                    currency,
                    date,
                    Type.EXPENSE);

            return ResponseEntity.ok(res);
        } catch (WrongDateParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date value");
        }

    }

    @PostMapping(path="add/income")
    ResponseEntity addIncome(@RequestParam String description,
                             @RequestParam double amount,
                             @RequestParam Currency currency,
                             @RequestParam String date) {
        try {
            RecordView res = recordService.addRecord(
                    description,
                    amount,
                    currency,
                    date,
                    Type.INCOME);
            return ResponseEntity.ok(res);
        } catch (WrongDateParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date value");
        }
    }

    @GetMapping(path="get/expenses")
    ResponseEntity getExpenses() {
        return ResponseEntity.ok(recordService.getRecords(Type.EXPENSE));
    }

    @GetMapping(path="get/incomes")
    ResponseEntity getIncomes() {
        return ResponseEntity.ok(recordService.getRecords(Type.INCOME));
    }
}
