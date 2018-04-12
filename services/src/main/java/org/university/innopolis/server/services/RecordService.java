package org.university.innopolis.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.persistence.RecordRepository;
import org.university.innopolis.server.model.Record;
import org.university.innopolis.server.services.exceptions.WrongAmountValueException;
import org.university.innopolis.server.services.exceptions.WrongDateParameterException;
import org.university.innopolis.server.views.RecordView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RecordService {
    private RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public RecordView addRecord(String description,
                                double amount,
                                Currency currency,
                                long date,
                                Type type) throws WrongAmountValueException,
                                                WrongDateParameterException{

        checkAmount(amount);
        Date properDate = convertDate(date * 1000L);
        Record res = new Record(amount, currency, properDate, type);
        if (!(description == null || "".equals(description))) {
            res.setDescription(description);
        }
        res = recordRepository.save(res);
        return new RecordView(res);


    }

    public List<RecordView> getRecords(Type type) {
        List<Record> records = recordRepository.getByType(type);

        List<RecordView> recordViews = new ArrayList<>();

        for(Record r: records) {
            recordViews.add(new RecordView(r));
        }

        return recordViews;
    }

    public List<RecordView> getAllRecords() {

        List<Record> records = recordRepository.findAll();

        List<RecordView> recordViews = new ArrayList<>();

        for(Record r: records) {
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