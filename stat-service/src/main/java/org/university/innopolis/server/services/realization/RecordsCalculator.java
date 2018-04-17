package org.university.innopolis.server.services.realization;

import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.views.RecordView;

import java.util.Map;

public interface RecordsCalculator {
    void registerRecord(int accountId, RecordView record);

    void fillMap(int accountId, Map<String, Double> res);

    Type getType();
}
