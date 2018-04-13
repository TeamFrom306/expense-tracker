package org.university.innopolis.server.stat;

import java.util.Map;

class DayRecordsCalculator extends RecordsCalculator {
    private static final long TIME_QUANTIFICATION = 84600; // 3600 * 24

    @Override
    protected long getQuantificationTime() {
        return TIME_QUANTIFICATION;
    }

    @Override
    public void fillMap(int accountId, Map<String, Double> res) {
        res.put("dayAvg", getAverage(accountId));
    }
}
