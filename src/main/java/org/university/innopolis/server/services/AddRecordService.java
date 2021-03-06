package org.university.innopolis.server.services;

import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.services.exceptions.WrongAmountValueException;
import org.university.innopolis.server.services.exceptions.WrongDateParameterException;
import org.university.innopolis.server.views.RecordView;

public interface AddRecordService {
    RecordView addRecord(String description,
                         double amount,
                         Currency currency,
                         long date,
                         Type type,
                         int holderId) throws
            WrongAmountValueException,
            WrongDateParameterException;
}
