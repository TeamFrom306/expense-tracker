package org.university.innopolis.server.services_realization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.services.AddRecordService;
import org.university.innopolis.server.services.AvgRecordService;
import org.university.innopolis.server.services.exceptions.WrongAmountValueException;
import org.university.innopolis.server.services.exceptions.WrongDateParameterException;
import org.university.innopolis.server.views.RecordView;

import java.util.Map;

@Service("proxyAddRecordService")
public class ProxyAddRecordService implements AddRecordService, AvgRecordService {
    private AddRecordService addRecordService;
    private StateManager stateManager;

    @Autowired
    public ProxyAddRecordService(AddRecordService addRecordService,
                                 StateManager stateManager) {
        this.addRecordService = addRecordService;
        this.stateManager = stateManager;
    }

    @Override
    public RecordView addRecord(String description,
                                double amount,
                                Currency currency,
                                long date,
                                Type type,
                                int accountId) throws
            WrongAmountValueException,
            WrongDateParameterException {
        RecordView record = addRecordService.addRecord(description, amount, currency, date, type, accountId);
        stateManager.appendRecord(accountId, record);

        return record;
    }

    @Override
    public Map<String, Double> getAvgStat(int accountId) {
        return stateManager.getStats(accountId);
    }
}
