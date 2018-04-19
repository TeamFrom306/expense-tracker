package org.university.innopolis.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.services.AddRecordService;
import org.university.innopolis.server.services.AvgRecordService;
import org.university.innopolis.server.services.GetRecordService;
import org.university.innopolis.server.services.exceptions.WrongAmountValueException;
import org.university.innopolis.server.services.exceptions.WrongDateParameterException;
import org.university.innopolis.server.views.RecordView;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin("*")
@RequestMapping(path="api/records/")
public class RecordController {
    private static Logger logger = LoggerFactory.getLogger(RecordController.class);
    private GetRecordService getRecordService;
    private AddRecordService addRecordService;
    private AvgRecordService avgRecordService;

    @Autowired
    public RecordController(GetRecordService getRecordService,
                            AddRecordService proxyAddRecordService,
                            AvgRecordService avgRecordService) {
        this.getRecordService = getRecordService;
        this.addRecordService = proxyAddRecordService;
        this.avgRecordService = avgRecordService;
    }

    @PostMapping(path="/expenses")
    ResponseEntity addExpense(@RequestParam(required = false) String description,
                              @RequestParam double amount,
                              @RequestParam Currency currency,
                              @RequestParam long date,
                              @RequestAttribute int accountId) {
        String logString = "/expenses, account: {}, amount: {}, date: {}, status: {}";
        try {
            RecordView res = addRecordService.addRecord(
                    description,
                    amount,
                    currency,
                    date,
                    Type.EXPENSE,
                    accountId);
            logger.debug(logString, accountId, amount, new Date(date), HttpStatus.OK);
            return ResponseEntity.ok(res);
        } catch (WrongDateParameterException e) {
            logger.debug(logString, accountId, amount, new Date(date), HttpStatus.BAD_REQUEST);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date value");
        } catch (WrongAmountValueException e) {
            logger.debug(logString, accountId, amount, new Date(date), HttpStatus.BAD_REQUEST);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount value");
        }

    }

    @PostMapping(path="/incomes")
    ResponseEntity addIncome(@RequestParam(required = false)  String description,
                             @RequestParam double amount,
                             @RequestParam Currency currency,
                             @RequestParam long date,
                             @RequestAttribute int accountId) {
        String logString = "/incomes, account: {}, amount: {}, date: {}, status: {}";

        try {
            RecordView res = addRecordService.addRecord(
                    description,
                    amount,
                    currency,
                    date,
                    Type.INCOME,
                    accountId);
            logger.debug(logString, accountId, amount, new Date(date), HttpStatus.OK);
            return ResponseEntity.ok(res);
        } catch (WrongDateParameterException e) {
            logger.debug(logString, accountId, amount, new Date(date), HttpStatus.BAD_REQUEST);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date value");
        } catch (WrongAmountValueException e) {
            logger.debug(logString, accountId, amount, new Date(date), HttpStatus.BAD_REQUEST);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount value");
        }
    }

    @GetMapping(path="/expenses")
    ResponseEntity getExpenses(@RequestAttribute int accountId) {
        List<RecordView> records = getRecordService.getRecords(Type.EXPENSE, accountId);
        logger.debug("/expenses, account: {}, status: {}", accountId, HttpStatus.OK);
        return ResponseEntity.ok(records);
    }

    @GetMapping(path="/incomes")
    ResponseEntity getIncomes(@RequestAttribute int accountId) {
        List<RecordView> records = getRecordService.getRecords(Type.INCOME, accountId);
        logger.debug("/incomes, account: {}, status: {}", accountId, HttpStatus.OK);
        return ResponseEntity.ok(records);
    }

    @GetMapping(path = "/stat")
    ResponseEntity getStat(@RequestAttribute int accountId) {
        Map<String, Double> avgStat = avgRecordService.getAvgStat(accountId);
        logger.debug("/stat, account: {}, status: {}", accountId, HttpStatus.OK);
        return ResponseEntity.ok(avgStat);
    }
}
