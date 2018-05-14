package org.university.innopolis.server.services;

import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.views.RecordView;

import java.util.List;

public interface GetRecordService {
    List<RecordView> getRecords(Type type, int accountId);

    List<RecordView> getAllRecords(int accountId);

    List<RecordView> getAllRecords(int accountId, int count, int offset);
}
