package org.university.innopolis.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.services.AddRecordService;
import org.university.innopolis.server.services.GetRecordService;
import org.university.innopolis.server.services.exceptions.WrongAmountValueException;
import org.university.innopolis.server.services.exceptions.WrongDateParameterException;
import org.university.innopolis.server.stat.AvgRecordService;
import org.university.innopolis.server.views.RecordView;
import org.university.innopolis.server.wrappers.ExpenseWrapper;
import org.university.innopolis.server.wrappers.IncomeWrapper;
import org.university.innopolis.server.wrappers.RecordWrapper;

import javax.validation.Valid;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "api/records/")
public class RecordController {

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

    @PostMapping(path = "/expenses")
    ResponseEntity addExpense(@Valid @RequestBody ExpenseWrapper wrapper,
                              @RequestAttribute int accountId) {
        return ResponseEntity.ok(addRecord(wrapper, accountId));
    }

    private RecordView addRecord(RecordWrapper wrapper, int accountId) {
        try {
            return addRecordService.addRecord(
                    wrapper.getDescription(),
                    wrapper.getAmount(),
                    wrapper.getCurrency(),
                    wrapper.getDate(),
                    wrapper.getType(),
                    accountId);
        } catch (WrongDateParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date value");
        } catch (WrongAmountValueException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount value");
        }
    }

    @PostMapping(path = "/incomes")
    ResponseEntity addIncome(@Valid @RequestBody IncomeWrapper wrapper,
                             @RequestAttribute int accountId) {
        return ResponseEntity.ok(addRecord(wrapper, accountId));
    }

    @GetMapping(path = "/expenses")
    ResponseEntity getExpenses(@RequestAttribute int accountId) {
        return ResponseEntity.ok(getRecordService.getRecords(Type.EXPENSE, accountId));
    }

    @GetMapping(path = "/incomes")
    ResponseEntity getIncomes(@RequestAttribute int accountId) {
        return ResponseEntity.ok(getRecordService.getRecords(Type.INCOME, accountId));
    }

    @GetMapping(path = "/all")
    ResponseEntity getAllRecords(@RequestAttribute int accountId) {
        return ResponseEntity.ok(getRecordService.getAllRecords(accountId));
    }

    @GetMapping(path = "/stat")
    ResponseEntity getStat(@RequestAttribute int accountId) {
        return ResponseEntity.ok(avgRecordService.getAvgStat(accountId));
    }
}
