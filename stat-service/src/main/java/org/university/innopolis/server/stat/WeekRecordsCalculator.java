package org.university.innopolis.server.stat;

import java.util.Map;

public class WeekRecordsCalculator extends RecordsCalculator {
    private static final long TIME_QUANTIFICATION = 592200; // 3600 * 24 * 7

    @Override
    protected long getQuantificationTime() {
        return TIME_QUANTIFICATION;
    }

    @Override
    public void fillMap(int accountId, Map<String, Double> res) {
        res.put("weekAvg", getAverage(accountId));
    }
}
