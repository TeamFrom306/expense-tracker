package org.university.innopolis.server.services.realization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.model.Account;
import org.university.innopolis.server.model.Record;
import org.university.innopolis.server.persistence.AccountRepository;
import org.university.innopolis.server.persistence.RecordRepository;
import org.university.innopolis.server.services.AddRecordService;
import org.university.innopolis.server.services.GetRecordService;
import org.university.innopolis.server.services.exceptions.WrongAccountIdException;
import org.university.innopolis.server.services.exceptions.WrongAmountValueException;
import org.university.innopolis.server.services.exceptions.WrongDateParameterException;
import org.university.innopolis.server.services.realization.mappers.RecordMapper;
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
                                int accountId,
                                int holderId) throws
            WrongAmountValueException,
            WrongDateParameterException,
            WrongAccountIdException {

        checkAmount(amount);
        Date properDate = tryParseDate(date);

        Account account = accountRepository.getById(accountId);
        if (account == null)
            return null;
        if (account.getHolder() == null || account.getHolder().getId() != holderId)
            throw new WrongAccountIdException();


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

        return RecordMapper.map(record);
    }

    @Override
    public List<RecordView> getRecords(Type type, int holderId) {
        List<Record> records = recordRepository.getRecordsByAccount_Holder_Id(holderId);

        records = records
                .stream()
                .filter(r -> r.getType() == type)
                .collect(Collectors.toList());

        List<RecordView> recordViews = new ArrayList<>();

        for (Record r : records) {
            recordViews.add(RecordMapper.map(r));
        }

        return recordViews;
    }

    @Override
    public List<RecordView> getAllRecords(int holderId) {
        List<Record> records = recordRepository.getRecordsByAccount_Holder_Id(holderId);

        List<RecordView> recordViews = new ArrayList<>();

        for (Record r : records) {
            recordViews.add(RecordMapper.map(r));
        }

        return recordViews;
    }

    @Override
    public List<RecordView> getAllRecords(int holderId, int count, int page) {
        Pageable pageable = PageRequest.of(page, count);
        List<Record> records = recordRepository.getRecordsByAccount_Holder_IdOrderByDateDesc(holderId, pageable);
        List<RecordView> recordViews = new ArrayList<>();

        for (Record r : records) {
            recordViews.add(RecordMapper.map(r));
        }

        return recordViews;
    }

    private void checkAmount(double amount) throws WrongAmountValueException {
        if (amount <= 0.0)
            throw new WrongAmountValueException(amount);
    }

    private Date tryParseDate(long date) throws WrongDateParameterException {
        long dateInMsec = date * 1000;
        long currentDate = new Date().getTime();
        if (date < 0 || currentDate - dateInMsec < -300_000)
            throw new WrongDateParameterException(date);
        return new Date(dateInMsec);
    }
}