package org.university.innopolis.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.model.Record;
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

    public RecordView addRecord(String description,
                                int amount,
                                Currency currency,
                                Date date,
                                Type type) {

        Record res = new Record(amount, currency, date, type);
        if (!(description == null || "".equals(description))) {
            res.setDescription(description);
        }
        res = recordRepository.save(res);
        return new RecordView(res);
    }
}