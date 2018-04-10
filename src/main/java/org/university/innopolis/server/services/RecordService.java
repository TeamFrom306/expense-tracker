package org.university.innopolis.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.model.Record;
import org.university.innopolis.server.persistence.RecordRepository;
import org.university.innopolis.server.services.exceptions.WrongDateParameterException;
import org.university.innopolis.server.views.RecordView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                                String date,
                                Type type) throws WrongDateParameterException {

        try {
            Date properDate = StringToDate(date);
            Record res = new Record(amount, currency, properDate, type);
            if (!(description == null || "".equals(description))) {
                res.setDescription(description);
            }
            res = recordRepository.save(res);
            return new RecordView(res);

        } catch (ParseException e) {
            throw new WrongDateParameterException(date);
        }


    }

    public RecordView[] getRecords(Type type) {
        Record[] records = recordRepository.getByType(type);

        RecordView[] recordViews = new RecordView[records.length];

        for(int i = 0; i < records.length; i++) {
            recordViews[i] = new RecordView(records[i]);
        }

        return recordViews;
    }

    private Date StringToDate(String date) throws ParseException {
        Date properDate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
        return properDate;
    }
}