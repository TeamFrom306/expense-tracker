package org.university.innopolis.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.model.Account;
import org.university.innopolis.server.persistence.AccountRepository;
import org.university.innopolis.server.persistence.RecordRepository;
import org.university.innopolis.server.model.Record;
import org.university.innopolis.server.services.exceptions.WrongAmountValueException;
import org.university.innopolis.server.services.exceptions.WrongDateParameterException;
import org.university.innopolis.server.views.RecordView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("addRecordService")
public class RecordService implements AddRecordService, GetRecordService {
    private RecordRepository recordRepository;
    private AccountRepository accountRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository,
                         AccountRepository accountRepository) {
        this.recordRepository = recordRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public RecordView addRecord(String description,
                                double amount,
                                Currency currency,
                                long date,
                                Type type,
                                int accountId) throws WrongAmountValueException,
            WrongDateParameterException {

        checkAmount(amount);
        Date properDate = convertDate(date * 1000L);
        Account account = accountRepository.getById(accountId);

        Record record = new Record(amount, currency, properDate, type);
        record.setAccount(account);

        if (!(description == null || "".equals(description))) {
            record.setDescription(description);
        }

        account.setBalance(record.getType() == Type.INCOME ?
                account.getBalance() + record.getAmount() :
                account.getBalance() - record.getAmount());

        accountRepository.save(account);
        record = recordRepository.save(record);

        return new RecordView(record);


    }

    @Override
    public List<RecordView> getRecords(Type type, int accountId) {
        Account account = accountRepository.getById(accountId);
        List<Record> records = account.getRecords();
        records = records
                .stream()
                .filter(r -> r.getType() == type)
                .collect(Collectors.toList());

        List<RecordView> recordViews = new ArrayList<>();

        for (Record r : records) {
            recordViews.add(new RecordView(r));
        }

        return recordViews;
    }

    @Override
    public List<RecordView> getAllRecords(int accountId) {
        Account account = accountRepository.getById(accountId);
        List<Record> records = account.getRecords();

        List<RecordView> recordViews = new ArrayList<>();

        for (Record r : records) {
            recordViews.add(new RecordView(r));
        }

        return recordViews;
    }

    private void checkAmount(double amount) throws WrongAmountValueException {
        if (amount <= 0.0)
            throw new WrongAmountValueException(amount);
    }

    private Date convertDate(long date) throws WrongDateParameterException {
        if (date <= 0) {
            throw new WrongDateParameterException(Long.toString(date));
        }
        return new Date(date);
    }
}