package org.university.innopolis.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.services.AccountService;
import org.university.innopolis.server.services.RecordService;
import org.university.innopolis.server.services.exceptions.WrongAmountValueException;
import org.university.innopolis.server.services.exceptions.WrongCurrencyTypeException;
import org.university.innopolis.server.services.exceptions.WrongDateParameterException;
import org.university.innopolis.server.views.RecordView;

import java.util.Date;

@Controller
@RequestMapping(path="api/")
public class RecordController {

    private RecordService recordService;
    private AccountService accountService;

    @Autowired
    public RecordController(RecordService recordService, AccountService accountService) {
        this.recordService = recordService;
        this.accountService = accountService;
    }

    @PostMapping(path="/expenses")
    ResponseEntity addExpense(@RequestParam String description,
                              @RequestParam double amount,
                              @RequestParam Currency currency,
                              @RequestParam long date,
                              @RequestAttribute int accountId) {
        try {
            RecordView res = recordService.addRecord(
                    description,
                    amount,
                    currency,
                    date,
                    Type.EXPENSE);
            accountService.withdrawMoney(accountId, amount);
            return ResponseEntity.ok(res);
        } catch (WrongDateParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date value");
        } catch (WrongAmountValueException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount value");
        }

    }

    @PostMapping(path="/incomes")
    ResponseEntity addIncome(@RequestParam String description,
                             @RequestParam double amount,
                             @RequestParam Currency currency,
                             @RequestParam long date,
                             @RequestAttribute int accountId) {
        try {
            RecordView res = recordService.addRecord(
                    description,
                    amount,
                    currency,
                    date,
                    Type.INCOME);
            accountService.addMoney(accountId, amount);
            return ResponseEntity.ok(res);
        } catch (WrongDateParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date value");
        } catch (WrongAmountValueException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount value");
        }
    }

    @GetMapping(path="/expenses")
    ResponseEntity getExpenses() {
        return ResponseEntity.ok(recordService.getRecords(Type.EXPENSE));
    }

    @GetMapping(path="/incomes")
    ResponseEntity getIncomes() {
        return ResponseEntity.ok(recordService.getRecords(Type.INCOME));
    }
}
