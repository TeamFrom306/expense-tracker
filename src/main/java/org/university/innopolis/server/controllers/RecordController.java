package org.university.innopolis.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.services.AddRecordService;
import org.university.innopolis.server.services.AvgRecordService;
import org.university.innopolis.server.services.GetRecordService;
import org.university.innopolis.server.services.exceptions.WrongAmountValueException;
import org.university.innopolis.server.services.exceptions.WrongDateParameterException;
import org.university.innopolis.server.views.RecordView;
import org.university.innopolis.server.wrappers.ExpenseWrapper;
import org.university.innopolis.server.wrappers.IncomeWrapper;
import org.university.innopolis.server.wrappers.RecordWrapper;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "api/records/")
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

    @PostMapping(path = "/expenses")
    ResponseEntity addExpense(@Valid @RequestBody ExpenseWrapper wrapper,
                              @RequestAttribute int holderId) {
        return ResponseEntity.ok(addRecord(wrapper, holderId));
    }

    @PostMapping(path = "/incomes")
    ResponseEntity addIncome(@Valid @RequestBody IncomeWrapper wrapper,
                             @RequestAttribute int holderId) {
        return ResponseEntity.ok(addRecord(wrapper, holderId));
    }

    private RecordView addRecord(RecordWrapper wrapper, int holderId) {
        String logString = "/expenses, holder: {}, amount: {}, date: {}, status: {}";
        try {
            RecordView res = addRecordService.addRecord(
                    wrapper.getDescription(),
                    wrapper.getAmount(),
                    wrapper.getCurrency(),
                    wrapper.getDate(),
                    wrapper.getType(),
                    holderId);

            logger.debug(logString,
                    holderId,
                    wrapper.getAmount(),
                    new Date(wrapper.getDate()),
                    HttpStatus.OK);

            return res;
        } catch (WrongDateParameterException e) {
            logger.debug(logString,
                    holderId,
                    wrapper.getAmount(),
                    new Date(wrapper.getDate()),
                    HttpStatus.BAD_REQUEST);

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date value");
        } catch (WrongAmountValueException e) {
            logger.debug(logString,
                    holderId,
                    wrapper.getAmount(),
                    new Date(wrapper.getDate()),
                    HttpStatus.BAD_REQUEST);

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount value");
        }
    }

    @GetMapping(path = "/expenses")
    ResponseEntity getExpenses(@RequestAttribute int holderId) {
        List<RecordView> records = getRecordService.getRecords(Type.EXPENSE, holderId);
        logger.debug("/expenses, holder: {}, status: {}", holderId, HttpStatus.OK);
        return ResponseEntity.ok(records);
    }

    @GetMapping(path = "/incomes")
    ResponseEntity getIncomes(@RequestAttribute int holderId) {
        List<RecordView> records = getRecordService.getRecords(Type.INCOME, holderId);
        logger.debug("/incomes, holder: {}, status: {}", holderId, HttpStatus.OK);
        return ResponseEntity.ok(records);
    }

    @GetMapping(path = "/all")
    ResponseEntity getAllRecords(@RequestParam(defaultValue = "20", required = false) int count,
                                 @RequestParam(defaultValue = "0", required = false) int page,
                                 @RequestAttribute int holderId) {
        logger.debug("/all, holder: {}, count: {}, page: {}, status: {}",
                holderId,
                count,
                page,
                HttpStatus.OK);
        return ResponseEntity.ok(getRecordService.getAllRecords(holderId, count, page));
    }

    @GetMapping(path = "/stat")
    ResponseEntity getStat(@RequestAttribute int holderId) {
        Map<String, Double> avgStat = avgRecordService.getAvgStat(holderId);
        logger.debug("/stat, holder: {}, status: {}", holderId, HttpStatus.OK);
        return ResponseEntity.ok(avgStat);
    }
}
