package org.university.innopolis.server.services_realization.calculators;

import org.university.innopolis.server.services_realization.RecordsCalculator;
import org.university.innopolis.server.views.RecordView;

import java.util.*;

public abstract class RecordsCalculatorBase implements RecordsCalculator {
    private Map<Integer, Double> total = new HashMap<>();
    private Map<Integer, List<RecordView>> queue = new HashMap<>();

    @Override
    public void registerRecord(int accountId, RecordView record) {
        queue.computeIfAbsent(accountId, k -> new ArrayList<>());
        total.putIfAbsent(accountId, 0d);

        long startTime = computeTime();
        if (record.getDate().getTime() / 1000 < startTime)
            return;

        List<RecordView> recordList = queue.get(accountId);
        recordList.add(record);
        total.compute(accountId, (k, v) -> v + record.getAmount());
    }

    double getAverage(int accountId) {
        List<RecordView> list = filterByTime(accountId);

        if (list == null)
            return 0;

        return total.get(accountId) / (list.isEmpty() ? 1 : list.size());
    }

    private List<RecordView> filterByTime(int accountId) {
        List<RecordView> list = queue.get(accountId);
        if (list == null)
            return null;
        long startTime = computeTime();
        int i = 0;
        while (i < list.size()) {
            RecordView record = list.get(i);
            if (record.getDate().getTime() / 1000 < startTime) {
                total.compute(accountId, (k, v) -> v - record.getAmount());
                list.remove(i);
            } else {
                i++;
            }
        }
        return list;
    }

    private long computeTime() {
        long time = new Date().getTime() / 1000;
        return time - (time % getQuantificationTime());
    }

    protected abstract long getQuantificationTime();

}