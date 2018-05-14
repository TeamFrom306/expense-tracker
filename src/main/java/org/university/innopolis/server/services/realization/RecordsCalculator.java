package org.university.innopolis.server.services.realization;

import org.university.innopolis.server.common.JsonSerializable;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.views.RecordView;

import java.util.Map;

public interface RecordsCalculator extends JsonSerializable {
    void registerRecord(int holderId, RecordView record);

    void fillMap(int holderId, Map<String, Double> res);

    Type getType();
}
