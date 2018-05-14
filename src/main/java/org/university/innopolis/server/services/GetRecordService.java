package org.university.innopolis.server.services;

import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.views.RecordView;

import java.util.List;

public interface GetRecordService {
    List<RecordView> getRecords(Type type, int holderId);

    List<RecordView> getAllRecords(int holderId);

    List<RecordView> getAllRecords(int holderId, int count, int offset);
}
