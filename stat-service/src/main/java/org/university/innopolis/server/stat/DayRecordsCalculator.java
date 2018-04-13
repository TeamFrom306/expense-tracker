package org.university.innopolis.server.stat;

import org.university.innopolis.server.views.RecordView;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

class DayRecordsCalculator implements RecordsCalculator {
    private Map<Integer, Double> total = new HashMap<>();
    private Map<Integer, LinkedList<RecordView>> queue = new HashMap<>();
    private static final long TIME_QUANTIFICATION = 84600;

    @Override
    public void registerRecord(int accountId, RecordView record) {
        queue.computeIfAbsent(accountId, k -> new LinkedList<>());
        total.putIfAbsent(accountId, 0d);

        LinkedList<RecordView> recordList = queue.get(accountId);
        recordList.addLast(record);

        total.compute(accountId, (k, v) -> v + record.getAmount());

        long time = new Date().getTime() / 1000;
        long startTime = time - (time % TIME_QUANTIFICATION);

        while (!recordList.isEmpty() &&
                recordList.getFirst().getDate().getTime() / 1000 < startTime) {
            RecordView first = recordList.getFirst();
            total.compute(accountId, (k, v) -> v - first.getAmount());
            recordList.removeFirst();
        }
    }
}
