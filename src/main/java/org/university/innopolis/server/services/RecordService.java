package org.university.innopolis.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.innopolis.server.model.Currency;
import org.university.innopolis.server.model.Record;
import org.university.innopolis.server.model.Type;
import org.university.innopolis.server.persistence.RecordRepository;
import org.university.innopolis.server.views.RecordView;

import java.util.Date;

@Service
public class RecordService {
    private RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public RecordView addExpense(String description, int amount,
                                 Currency currency, Date date,
                                 Type type) {

        Record res;
        if (description == null || "".equals(description)) {
            res = recordRepository.save(new Record(amount, currency, date, type));
        } else {
            res = recordRepository.save(new Record(amount, currency, date, description, type));
        }
        return new RecordView(res);
    }

    public RecordView addIncome(String description, int amount,
                                Currency currency, Date date,
                                Type type) {
        Record res;

        if(description == null || "".equals(description)) {
            res = recordRepository.save(new Record(amount, currency, date, type));
        } else {
            res = recordRepository.save(new Record(amount, currency, date, description, type));
        }

        return new RecordView(res);
    }

}