package org.university.innopolis.server.services.realization;

import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.views.RecordView;

import java.util.Map;

public interface RecordsCalculator extends JsonExportable, JsonImportable {
    void registerRecord(int accountId, RecordView record);

    void fillMap(int accountId, Map<String, Double> res);

    Type getType();
}
