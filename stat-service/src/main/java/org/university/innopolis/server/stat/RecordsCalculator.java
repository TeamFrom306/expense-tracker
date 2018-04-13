package org.university.innopolis.server.stat;

import org.university.innopolis.server.views.RecordView;

interface RecordsCalculator {
    void registerRecord(int accountId, RecordView record);
}
